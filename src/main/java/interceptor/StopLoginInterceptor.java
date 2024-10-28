package interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.student.pojo.UserOA;



public class StopLoginInterceptor implements HandlerInterceptor {
	
	private List<String> excludeURIS;//��������δ��¼��������������
	public List<String> getExcludeURIS() {
		return excludeURIS;
	}

	public void setExcludeURIS(List<String> excludeURIS) {
		this.excludeURIS = excludeURIS;
	}
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean isok = false;
		//1.��������uri
		String baseuri=request.getRequestURI();
		String projecturi = request.getContextPath();
		String uri = baseuri.replace(projecturi, "");
		
		//2.����URI�Ƿ��ڰ������У�������У������жϲ�����
		if(this.excludeURIS.contains(uri)){
			isok = true;
		}else{
		//3.�ж�session���ö������Ƿ���suserK-V
			HttpSession session = request.getSession();
			UserOA user= session.getAttribute("userOA")==null?null:(UserOA)session.getAttribute("userOA");
		    if(user != null){
		    	isok = true;
		    }else{
		    	//���á���ʾ��Ϣ��
		    	session.setAttribute("errmsg", "�Ự�쳣�������µ�¼��");
		    	response.sendRedirect(request.getContextPath()+"/login.jsp");
		    }
		}
			return isok;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {	
	}
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		
	}

}

