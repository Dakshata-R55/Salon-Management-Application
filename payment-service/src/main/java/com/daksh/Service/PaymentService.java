package com.daksh.Service;

import com.daksh.Model.PaymentOrder;
import com.daksh.Payload.response.PaymentLinkResponse;
import com.daksh.domain.PaymentMethod;
import com.daksh.dto.BookingDto;
import com.daksh.dto.UserDTO;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {

PaymentLinkResponse createOrder(UserDTO user, BookingDto booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException;


PaymentOrder getPaymentOrderById(Long id) throws Exception;
PaymentOrder getPaymentOrderByPaymentId(String paymentId);
PaymentLink createRazorPayment (UserDTO user, Long amount, Long orderId) throws RazorpayException;

String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException;

Boolean proceedPayment(PaymentOrder paymentOrder,String paymentId,String paymentLinkId) throws RazorpayException;

}
