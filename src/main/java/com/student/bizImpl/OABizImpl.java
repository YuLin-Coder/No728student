package com.student.bizImpl;

import java.util.List;

import com.student.Userdao.OAUserdao;
import com.student.biz.OAUserdaoBiz;
import com.student.pojo.OAClass;
import com.student.pojo.OAStudent;
import com.student.pojo.TeacherOA;
import com.student.pojo.UserLog;
import com.student.pojo.UserOA;

public class OABizImpl implements OAUserdaoBiz {
	private OAUserdao oaUserdao;
	public void setOaUserdao(OAUserdao oaUserdao) {
		this.oaUserdao = oaUserdao;
	}
	//��¼
	@Override
	/*public UserOA OaLogin(String username, String password) {
		return this.oaUserdao.OaLogin(username, password);
	}*/
	public UserOA OaLogin(UserOA userOA) {
		return this.oaUserdao.OaLogin(userOA);
	}
	//��ѯ
	@Override
	public List<OAStudent> getSelectMess(String text1, String text2) {
		return this.oaUserdao.getSelectMess(text1, text2);
	}
	//ɾ��
		@Override
		public int deleteStudent(Integer[] id) {
			int num=0;
			for(int i=0; i<id.length;i++){
				num=oaUserdao.deleteStudent(id[i]);
			}
			return num;
		}
		//���
		@Override
		public int saveStudent(OAStudent oaStudent) {
			return this.oaUserdao.saveStudent(oaStudent);
		}
		//�޸Ĵ�ֵ
		@Override
		public OAStudent getupdate(Integer id) {
			return this.oaUserdao.getupdate(id);
		}
		//ɾ��Student
		@Override
		public int updateStudent(OAStudent id) {
			return this.oaUserdao.updateStudent(id);
		}
		//�༶��ѯ
		@Override
		public List<OAClass> ClassSelect(String text3, String text4){
			return this.oaUserdao.ClassSelect(text3, text4);
		} 
	    //�༶ɾ��
		@Override
		public int deleteClass(Integer[] id) {
			int num=0;
			for(int i=0; i<id.length;i++){
				num=oaUserdao.deleteClass(id[i]);
			}
			return num;
		}
		//�༶���
		@Override
		public int saveClass(OAClass oaClass) {
			return this.oaUserdao.saveClass(oaClass);
		}
		//�޸Ĵ�ֵ
		@Override
		public OAClass getupdateOAClass(Integer id) {
			return this.oaUserdao.getupdateOAClass(id);
		}
		//�༶�޸�
		@Override
		public int updateClass(OAClass id) {
			return this.oaUserdao.updateClass(id);
		}
		//���ұ��ѯ
		@Override
		public List<TeacherOA> TeacherSelect(String text5, String text6) {
			return this.oaUserdao.TeacherSelect(text5, text6);
		}
		@Override
		public int deleteTeacher(Integer[] tid) {
			int num=0;
			for(int i=0; i<tid.length;i++){
				num=oaUserdao.deleteTeacher(tid[i]);
			}
			return num;
		}
		@Override
		public int saveTeacher(TeacherOA teacherOA) {
			return this.oaUserdao.saveTeacher(teacherOA);
		}
		@Override
		public TeacherOA getupdateOATeacher(Integer id) {
			return this.oaUserdao.getupdateOATeacher(id);
		}
		@Override
		public int updateTeacher(TeacherOA id) {
			return this.oaUserdao.updateTeacher(id);
		}
		@Override
		public int UserOAs(UserOA id) {
			return this.oaUserdao.UserOAs(id);
		}
		@Override
		public List<OAStudent> AllclassStudent(String one, String two,Integer id) {
			return this.oaUserdao.AllclassStudent(one, two,id);
		}
		@Override
		public int SavaZhuce(UserOA userOA) {
			// TODO Auto-generated method stub
			return this.oaUserdao.SavaZhuce(userOA);
		}
		@Override
		public List<UserLog> UserLogs(String text7, String text8) {
			// TODO Auto-generated method stub
			return this.oaUserdao.UserLogs(text7, text8);
		}
		@Override
		public List<OAClass> getAllClass() {
			return this.getAllClass();
		}
}
