package com.student.biz;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.student.pojo.OAClass;
import com.student.pojo.OAStudent;
import com.student.pojo.TeacherOA;
import com.student.pojo.UserLog;
import com.student.pojo.UserOA;

public interface OAUserdaoBiz {
	//OAҳ���¼
	/*public UserOA OaLogin(@Param("username")String username,@Param("password")String password);*/
	public UserOA OaLogin(UserOA userOA);
	//Student��Ϣ��ѯ
	public List<OAStudent> getSelectMess(String text1,String text2);
	//Student��Ϣɾ��
	int deleteStudent(@Param("id")Integer[] id);
	//Student��Ϣ���
	int saveStudent(OAStudent oaStudent);
	//�޸Ĵ�ֵ
    public OAStudent getupdate(Integer id);
    //�޸�Student
    int updateStudent(OAStudent id);
    //Class�༶��ѯ
  	public List<OAClass> ClassSelect(String text3,String text4);
    //Class�༶ɾ��
  	int deleteClass(@Param("id")Integer[] id);
  	//Class�༶���
  	int saveClass(OAClass oaClass);
  	//Class�༶�޸Ĵ�ֵ
    public OAClass getupdateOAClass(@Param("id")Integer id);
      //Class�༶�޸�
    int updateClass(OAClass id);
    //���ұ��ѯ
    public List<TeacherOA> TeacherSelect(String text5,String text6);
    //��ʦ��ɾ��
    int deleteTeacher(Integer[] tid);
    //��ʦ�����
  	int saveTeacher(TeacherOA teacherOA);
  	//��ʦ���޸Ĵ�ֵ
    public TeacherOA getupdateOATeacher(@Param("id")Integer id);
    //��ʦ���޸�
    int updateTeacher(TeacherOA id);
    //�����޸�
    int UserOAs(UserOA id);
    //���༶��ѯ����ѧ��
    public List<OAStudent> AllclassStudent(String one,String two,Integer id);
    //�û�ע��
    int SavaZhuce(UserOA userOA);
    //��־��ѯ
    public List<UserLog> UserLogs(@Param("text7")String text7,@Param("text8")String text8);
    //������༶��ѯ
    List<OAClass> getAllClass();
}
