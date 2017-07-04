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
import com.alipay.api.internal.util.AlipaySignature;

public class PayResult extends HttpServlet {
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArJAH9xOIiBeamFAKHt+/58JBTno0SMNY7S5CG+weQGDhcVnDY37niuPR/OjKOtj59yZnwFijG9RMktblkLjzso8n/NyjR+ipwLHler6/6FQ5ZbmV6LPU6zauhlXIhBxPoFzBYkxGsjgqpJZbQOwXPm0J5TlQ7/WRZLblf9gTr++Izv6it/HPJQT1izxcwW3X87ak4y8vgyWFoz5nzU8MDcwtWV/OK14hl9fA+mIa350qNoKQy3mZeI6cTYx90eaOfOrwHclIOvQV41APFqa2poRA6wQBBrcPqdNRRON1CDrPsYQLFrTZCyJ48FRnwcljJAPIYuCdVI11AxIjViJjvwIDAQAB";
	/**
	 * Constructor of the object.
	 */
	public PayResult() {
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

		doPost(request, response);
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

		System.out.println("doPost**********************");
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  }
		    System.out.println("name " + name + ",valueStr " + valueStr);
		  //乱码解决，这段代码在出现乱码时使用。
		  //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		  params.put(name, valueStr);
		 }
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean flag = false;
		try {
			flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "utf-8", "RSA2");
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag) {
			response.getWriter().write("success");
		} else {
			response.getWriter().write("failure");
		}
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
