package com.daksh.Service.impl;

import com.daksh.Model.PaymentOrder;
import com.daksh.Payload.response.PaymentLinkResponse;
import com.daksh.Repository.PaymentOrderRepository;
import com.daksh.Service.PaymentService;
import com.daksh.domain.PaymenOrderStatus;
import com.daksh.domain.PaymentMethod;
import com.daksh.dto.BookingDto;
import com.daksh.dto.UserDTO;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

private final PaymentOrderRepository paymentOrderRepository;

@Value("${stripe.api.key}")
private String stripeSecretKey;

@Value("${razorpay.api.key}")
private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;

    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDto booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException {
      Long amount =(long)booking.getTotalPrice();
     PaymentOrder order = new PaymentOrder();
order.setAmount(amount);
order.setPaymentMethod(paymentMethod);
order.setBookingId(booking.getId());
order.setSalonId(booking.getSalonId());
PaymentOrder savedOrder = paymentOrderRepository.save(order);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();


if(paymentMethod.equals(PaymentMethod.RAZORPAY))
{
    PaymentLink payment=createRazorPayment(user,savedOrder.getAmount(),savedOrder.getId());
String paymentUrl=payment.get("short_url");
String paymentUrlId=payment.get("id");
paymentLinkResponse.setPaymentLinkUrl(paymentUrl);
paymentLinkResponse.setPaymentLinkId(paymentUrlId);
savedOrder.setPaymentLinkId(paymentUrlId);
paymentOrderRepository.save(savedOrder);
}

else {
    String paymentUrl=createStripePaymentLink(user,savedOrder.getAmount(),savedOrder.getId());
    paymentLinkResponse.setPaymentLinkUrl(paymentUrl);
}


        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {

        PaymentOrder paymentOrder=paymentOrderRepository.findById(id).orElse(null);
if(paymentOrder==null)
{
    throw new Exception("Payment order not found");
}

        return paymentOrder ;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public PaymentLink createRazorPayment(UserDTO user, Long Amount, Long orderId) throws RazorpayException {

      Long amount=Amount*100;


          RazorpayClient razorpay = new RazorpayClient(razorpayApiKey,razorpayApiSecret);

        JSONObject paymentlinkRequest = new JSONObject();
paymentlinkRequest.put("amount",amount);
paymentlinkRequest.put("currency","INR");

JSONObject customer = new JSONObject();
customer.put("name",user.getFullName());
customer.put("email",user.getEmail());
        paymentlinkRequest.put("customer",customer);

JSONObject notify=new JSONObject();
notify.put("email",true);

paymentlinkRequest.put("notify",notify);

paymentlinkRequest.put("reminder_enable",true);

paymentlinkRequest.put("callback_url","http://localhost:3000/payment-success/"+orderId);

paymentlinkRequest.put("callback_method","get");

return razorpay.paymentLink.create(paymentlinkRequest);


    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey=stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder().addAllPaymentMethodType(Collections.singletonList(SessionCreateParams.PaymentMethodType.CARD)).setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl("http://localhost:3000/payment-success/"+orderId)
                .setCancelUrl("http://localhost:3000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L).setPriceData(SessionCreateParams.LineItem.PriceData.builder().setCurrency("usd")
                        .setUnitAmount(amount*100).setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName("salon appointment booking").build()).build()).build()).build();

        Session session = Session.create(params);

        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException {


       if(paymentOrder.getStatus().equals(PaymenOrderStatus.PENDING))
       {
           if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY))
           {
               RazorpayClient razorpay=new RazorpayClient(razorpayApiKey,razorpayApiSecret);
               Payment payment=razorpay.payments.fetch(paymentId);
               String status=payment.get("status");

               if(status.equals("captured"))
               {
                   //produce kafka
                   paymentOrder.setStatus(PaymenOrderStatus.SUCCESS);
                   paymentOrderRepository.save(paymentOrder);
                   return true;
               }
               return false;
           }
           else {

               paymentOrder.setStatus(PaymenOrderStatus.SUCCESS);
               paymentOrderRepository.save(paymentOrder);
               return true;
           }
       }

        return false;
    }
}
