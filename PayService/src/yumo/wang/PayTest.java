package yumo.wang;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

public class PayTest extends HttpServlet {

	public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPir1asRSw5AsmZp7wFo3cKcx8MA3ds+iji0IaUfLUzYGlSzwPCfiWPAN50xBd9L53AoMtQTj8F6EbIbiv8qr38SxI0EwQmuyUPnZIZt5MMyAcacFfIVfLSAl1zR1ObTSk+Awi9Ksh5/BYzhqPDQFVLART7lOv+mHfezcxFDPgnlQ3f0uM8nyC3ec/gDLPf/Bs4zOHSc3xPL41kRmnpchc8d8kcYzV43RGqDj1xtjygcTJDnjmT8exhI4pqGmMKcN5N1e7veMwVhQIU2ktU3d7z0GtM3KbsuGWL2d0LZimeiAa3FcvWH0CqC+nLweSGtphgVPLVGMP25LT8gVdSNRPAgMBAAECggEAE4c4g6IbwoUDz1GexeMLoaw+GZcgC81yFO3ZLGOBudnLjYVSwmkE3MuFXYmNbHrjfqVk2z9IVWORk3NfAPDuuZSiBbXw40FwdiqZRPhZvdwmvjbVC/ApVYqTXuds2UKaKudkH01SCubXF/3SNyv5xnCaliCSWhqiPhkxRQgOlrw+oJT7IKrjazSyuLQzFjhWuya06zWoUSiWMWv0PKFtOqn9p7NAmZIrdm9xfzeZadGUXteq2YStIOve15M+Dpfnrjc5dgcCuhxgZzovSC1IyWukrRSqfPWgaYrjmke/mYXpMztepnlQHhKFW82Ed/rcn7inLcnl7cIgM2lmmd/7oQKBgQDj03p2InqCfeYrlr8PUCMwvmBIlFhOQZ/ivIT7NmxWoUVbHgasMVFnTr7zEwCaUaXVr4ucAOlLZUR7OagPgRmyMQJEah9K+OZJO/PCYpuh/5OoYGsenQNvb4Ilvq3TL2GoydaMKJfobNAEb+0fr5VIESgGz6lMKJ8kCeKm6rzf5QKBgQChSv4uQyHWPbM6Lu4NCEeWz1EvkvhJ2v/9wVdMQCLD9n/UfHT5xXPuK9rNyp/d4lo0r7vHnWc2py69C7wy8Qzmug4JLRVxQBN+5OCOQtWqzGKzzzaYoNmObY8iF3wKzEVLS9aJKiCFNo7pObOZZkYK1PatKDL2/CMcDakYS4nYIwKBgQC6qcrsf9Ngl0a+8AQax84NYe4BiYnQlHQrkcpjCXQ6hmgM/8z3yHCp9Br/jdIwnjUBn5MDfrumypIRZGwOR/iFxyHUbB63jrcfyb8uxRw+3uhcTKN3sa3e374Crvg8z9V0NetTau8LwBuvhwUBsRresS/aZMqRW7cIEBFkMXAPAQKBgAgiSQk6N3WfbO9tOHINzdZsJBL7HWxUD/7TBj0BKv+o6a9ki12hOIR1T7Z7Fm7RIG1xw02AwZi++5trARWslFL6ZQTPcjpg2drXXf+unSnc3slklMNDsVT+b25vssC0pnXyoCIQrs0pADIAx+7fen7HWJ1I3rEX7AlZIwLnpE7zAoGAV5NWBNOhNrYCKVHzAOcgtuCYbEbftK0+qMn9WKgCfaVePeAQnd2FvzIFTVqp5TdDtprJDF8Q2HtWLzDnbesO/xUwZQg9B5TIjD07lAXW4P6tfVnxgh76GBMTbXzG+j4/+x8QsY+UhukADe+feuKC+8wSbo4jO0i9d1ahcWazVFo=";
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArJAH9xOIiBeamFAKHt+/58JBTno0SMNY7S5CG+weQGDhcVnDY37niuPR/OjKOtj59yZnwFijG9RMktblkLjzso8n/NyjR+ipwLHler6/6FQ5ZbmV6LPU6zauhlXIhBxPoFzBYkxGsjgqpJZbQOwXPm0J5TlQ7/WRZLblf9gTr++Izv6it/HPJQT1izxcwW3X87ak4y8vgyWFoz5nzU8MDcwtWV/OK14hl9fA+mIa350qNoKQy3mZeI6cTYx90eaOfOrwHclIOvQV41APFqa2poRA6wQBBrcPqdNRRON1CDrPsYQLFrTZCyJ48FRnwcljJAPIYuCdVI11AxIjViJjvwIDAQAB";
	/**
	 * Constructor of the object.
	 */
	public PayTest() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String outtradeno = "20170000004";
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", "2016090900474503", APP_PRIVATE_KEY, "json", 
				"utf-8", ALIPAY_PUBLIC_KEY, "RSA2");
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request1 = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("我是测试数据");
		model.setSubject("App支付测试Java");
		model.setOutTradeNo(outtradeno);
		model.setTimeoutExpress("30m");
		model.setTotalAmount("0.01");
		model.setProductCode("QUICK_MSECURITY_PAY");
		request1.setBizModel(model);
		request1.setNotifyUrl("http://www.emoreedu.com/PayService/servlet/PayResult");
		String result = null;
		try {
		        //这里和普通的接口调用不同，使用的是sdkExecute
		        AlipayTradeAppPayResponse response1 = alipayClient.sdkExecute(request1);
		        System.out.println(response1.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
		        result = response1.getBody();
		} catch (AlipayApiException e) {
		        e.printStackTrace();
		}
		response.getWriter().write(result);
	}


	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
