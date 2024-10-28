package com.student.Userdao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.student.pojo.OAClass;
import com.student.pojo.OAStudent;
import com.student.pojo.TeacherOA;
import com.student.pojo.UserLog;
import com.student.pojo.UserOA;
public interface OAUserdao {
	//OAҳ���¼
	/*public UserOA OaLogin(@Param("username")String username,@Param("password")String password);*/
	public UserOA OaLogin(UserOA userOA);
	//Student��Ϣ��ѯ
	public List<OAStudent> getSelectMess(@Param("text1")String text1,@Param("text2")String text2);
	//Student��Ϣɾ��
	int deleteStudent(@Param("id")Integer id);
	//Student��Ϣ���
	int saveStudent(OAStudent oaStudent);
	//�޸Ĵ�ֵ
    public OAStudent getupdate(@Param("id")Integer id);
    //�޸�Student
    int updateStudent(OAStudent id);
    //Class�༶��ѯ
  	public List<OAClass> ClassSelect(@Param("text3")String text3,@Param("text4")String text4);
    //Class�༶ɾ��
  	int deleteClass(@Param("id")Integer id);
  	//Class�༶���
  	int saveClass(OAClass oaClass);
  	//Class�༶�޸Ĵ�ֵ
    public OAClass getupdateOAClass(@Param("id")Integer id);
    //Class�༶�޸�
    int updateClass(OAClass id);
    //��ʦ���ѯ
    public List<TeacherOA> TeacherSelect(@Param("text5")String text5,@Param("text6")String text6);
    //��ʦ��ɾ��
    int deleteTeacher(@Param("tid")Integer tid);
    //��ʦ�����
  	int saveTeacher(TeacherOA teacherOA);
  	//��ʦ���޸Ĵ�ֵ
    public TeacherOA getupdateOATeacher(@Param("id")Integer id);
    //��ʦ���޸�
    int updateTeacher(TeacherOA id);
    //�����޸�
    int UserOAs(UserOA id);
    //�û�ע��
    int SavaZhuce(UserOA userOA);
    //��־��ѯ
    public List<UserLog> UserLogs(@Param("text7")String text7,@Param("text8")String text8);
    //���༶��ѯ����ѧ��
    public List<OAStudent> AllclassStudent(@Param("textone")String one,@Param("texttwo")String two,@Param("id")Integer id);
    //������༶��ѯ
    List<OAClass> getAllClass();
}
