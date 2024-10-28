
package interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.bdqn.entity.Employee;


public class StopLoginInterceptor implements HandlerInterceptor {
	
	private List<String> excludeURIS;//��������δ��¼��������������
	public List<String> getExcludeURIS() {
		return excludeURIS;
	}

	public void setExcludeURIS(List<String> excludeURIS) {
		this.excludeURIS = excludeURIS;
	}
	@Override
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
			Employee users=	session.getAttribute("user")==null?null:(Employee)session.getAttribute("user");
		    if(users != null){
		    	isok = true;
		    }else{
		    	//���á���ʾ��Ϣ��
		    	session.setAttribute("errmsg", "�Ự�쳣�������µ�¼��");
		    	response.sendRedirect(request.getContextPath()+"/login.jsp");
		    }
		}
			return isok;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}



}
