package com.Medhanialem.utils;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.payment.Payment;
import com.Medhanialem.model.payment.objects.MembershipReceiptHistory;
import com.Medhanialem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BuildReceiptByEmailUtility {

    @Autowired
    PaymentRepository paymentRepository;

    public String getReceiptBody(MembershipReceiptHistory membershipReceiptHistory) {
        StringBuilder sb = new StringBuilder();

        if (!membershipReceiptHistory.isVoided() || null == membershipReceiptHistory.getParentReceipt()) {
            sb.append("<p>Hi <b><i><span style=\"color:blue\">" + membershipReceiptHistory.getFullName() + "</span><i></b>. <br>Thank you! We have received your monthly membership payment and below is your receipt.</p>");
        } else {
            sb.append("<p>Hi <b><i><span style=\"color:blue\">" + membershipReceiptHistory.getFullName() + "</span><i></b>. <br>Thank you! Please find your monthly membership refund receipt below.</p>");
        }
        sb.append("<div class='nameMonthTotal'>");
        sb.append("<head><style type = \"text/css\">table {border-collapse:collapse;}</style></head>");
        sb.append("<table class='memberReceiptTbl' style=\"background-color: #F2FAFA; width: 80%\">");


        sb.append("<tr>");
        sb.append("<td style=\"padding-left: 10%; padding-right: 10%;\">");
        sb.append("<table style=\"margin-top: 25px;\">");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"padding-right: 50px; padding-left: 5px; text-align: center; font-size: 18px; color: #00008b;\">ናይ ኤርትራውያን ካቴድራል መድሓኔኣለም ኦርቶዶክስ ተዋሕዶ ቤተ ክርስትያን</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"padding-right: 50px; padding-left: 5px; text-align: center; font-size: 18px; color: #00008b;\">CATHEDRAL ERITREAN ORTHODOX TEWAHDO MEDHANEALEM CHURCH</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"font-weight: bold; padding-right: 50px; padding-left: 5px; text-align: center; font-size: 15px; color: #00008b;\"><i>1720 GUS THOMASSON RD.</i></td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"font-weight: bold; padding-right: 50px; padding-left: 5px; text-align: center; font-size: 15px; color: #00008b;\"><i>DALLAS TX 75228</i></td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<table class='memberPaymentInfoTbl' style=\"margin-top: 25px; margin-left: 5px; width: 40%;\">");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Full Name</td>");
        sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getFullName() + "</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Church Id</td>");
        sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getChurchId() + "</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Phone</td>");
        sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getPhone() + "</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Tier</td>");
        sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getTierDescription() + "</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Months</td>");
        sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getMonths() + "</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Date</td>");

        sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getCreatedDate() + "</td>");
        sb.append("</tr>");

        if (!membershipReceiptHistory.isVoided() || null == membershipReceiptHistory.getParentReceipt()) {
            sb.append("<tr>");
            sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Total</td>");
            sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">$" + membershipReceiptHistory.getTotal() + "</td>");
            sb.append("</tr>");
        } else {
            sb.append("<tr>");
            sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Total</td>");
            sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">-$" + (-1 * membershipReceiptHistory.getTotal()) + "</td>");
            sb.append("</tr>");
        }

        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Receipt No</td>");
        sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getReceiptId() + "</td>");
        sb.append("</tr>");

        if (membershipReceiptHistory.isVoided() && null != membershipReceiptHistory.getParentReceipt()) {
            sb.append("<tr>");
            sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Original Receipt No</td>");
            sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getParentReceipt() + "</td>");
            sb.append("</tr>");
        }

        if (membershipReceiptHistory.isForgiven()) {
            sb.append("<tr>");
            sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Forgiven</td>");
            sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">TRUE</td>");
            sb.append("</tr>");
        }

        if (membershipReceiptHistory.isForgiven()) {
            Long receiptId = membershipReceiptHistory.getReceiptId();
            Payment payment = paymentRepository.findById(receiptId).orElseThrow(
                    () -> new BackendException("There is no receipt found with receiptId = " + receiptId));
            sb.append("<tr>");
            sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Remark</td>");
            sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + payment.getRemark() + "</td>");
            sb.append("</tr>");
        }

        if (membershipReceiptHistory.isVoided() && null == membershipReceiptHistory.getParentReceipt()) {
            sb.append("<tr>");
            sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Voided</td>");
            sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">TRUE</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<table class='memberPaymentInfoMonthsTbl' style=\"margin-top: 8px; margin-left: 5px;\">");
        sb.append("<tr>");
        sb.append("<td id='leftLabels' style=\"padding-left: 5px;\"><b>Year: </b></td>");
        sb.append("<td id='leftLabels' style=\"padding-left: 5px;\">" + membershipReceiptHistory.getYear() + "</td>");
        sb.append("</tr>");
        sb.append("</table>");

        sb.append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td>");

        sb.append("<table class='memberPaymentInfoMonthsTbl' style=\"margin-bottom: 25px; margin-left: 5px; margin-right: 5px;\">");
        sb.append("<tr>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>January</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>February</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>March</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>April</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>May</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>June</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>July</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>August</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>September</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>October</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>November</b></td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>December</b></td>");
        sb.append("</tr>");

        Map<String, String> monthsPaymentDetail = new HashMap<>();
        fetchMonthsPaymentDetail(monthsPaymentDetail, membershipReceiptHistory);

        sb.append("<tr>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.JAN) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.FEB) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.MAR) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.APR) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.MAY) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.JUN) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.JUL) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.AUG) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.SEP) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.OCT) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.NOV) + "</td>");
        sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.DEC) + "</td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</table>");

        sb.append("<br><br><div>");
        sb.append("Regards,<br>");
        sb.append("Finance Department<br>");
        sb.append("Medhani Alem Eritrean Orthodox Church<br>");
        sb.append("Dallas, TX");
        sb.append("</div>");

        return sb.toString();
    }

    public Map<String, String> fetchMonthsPaymentDetail(Map<String, String> monthsPaymentDetail, MembershipReceiptHistory membershipReceiptHistory) {
        String monthsDetail = membershipReceiptHistory.getMonthsDetail();
        Boolean refund = (membershipReceiptHistory.isVoided() && null != membershipReceiptHistory.getParentReceipt()) ? true : false;
        monthsPaymentDetail.put(PaymentReceiptConstants.JAN, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.FEB, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.MAR, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.APR, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.MAY, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.JUN, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.JUL, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.AUG, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.SEP, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.OCT, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.NOV, PaymentReceiptConstants.EMPTY_STRING);
        monthsPaymentDetail.put(PaymentReceiptConstants.DEC, PaymentReceiptConstants.EMPTY_STRING);

        String[] monthDetailArray = monthsDetail.split(",");

        for (int i = 0; i < monthDetailArray.length; i++) {
            switch (monthDetailArray[i]) {
                case PaymentReceiptConstants.JAN:
                    monthsPaymentDetail.put(PaymentReceiptConstants.JAN, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.FEB:
                    monthsPaymentDetail.put(PaymentReceiptConstants.FEB, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.MAR:
                    monthsPaymentDetail.put(PaymentReceiptConstants.MAR, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.APR:
                    monthsPaymentDetail.put(PaymentReceiptConstants.APR, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.MAY:
                    monthsPaymentDetail.put(PaymentReceiptConstants.MAY, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.JUN:
                    monthsPaymentDetail.put(PaymentReceiptConstants.JUN, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.JUL:
                    monthsPaymentDetail.put(PaymentReceiptConstants.JUL, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.AUG:
                    monthsPaymentDetail.put(PaymentReceiptConstants.AUG, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.SEP:
                    monthsPaymentDetail.put(PaymentReceiptConstants.SEP, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.OCT:
                    monthsPaymentDetail.put(PaymentReceiptConstants.OCT, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.NOV:
                    monthsPaymentDetail.put(PaymentReceiptConstants.NOV, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
                case PaymentReceiptConstants.DEC:
                    monthsPaymentDetail.put(PaymentReceiptConstants.DEC, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
                    break;
            }
        }

        return monthsPaymentDetail;
    }
}
