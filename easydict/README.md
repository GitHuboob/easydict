����һ�������ֵ��滻����Ŀ��ͨ��mvn package���jar����������Ŀ����ʹ��

1.ʹ��ע�� @Dict ��ʵ�����б�ʶ��Ҫ�ֵ�ת�����ֶΣ�����dictTableΪ������dictType��dictCode��dictTextΪ�����ֵ����͡��ֵ���롢�ֵ����ƶ�Ӧ���ֶ�����dictTypeValueΪ���ֶε��������ƣ�dictTextSuffixΪ�ֵ����ƵĴ���ֶκ�׺��Ҫ��Ϊԭ�ֶ���+��׺����

public class Member {
    @Dict(dictTable = "dict", dictType = "type", dictCode = "code", dictText = "text", dictTypeValue = "name", dictTextSuffix = "Text")
    private String username;
    private String usernameText;
}

2.ʹ��ע�� @DictLoader �ڷ�����ʶ��Ҫ����ķ���������classNameΪ���ݿ�������urlΪ���ݿ��ַ��usernameΪ���ݿ��û�����passwordΪ���ݿ����롣ͬʱʹ��ע�� @DictReplace ��ʶ�ֵ��滻�ķ���TEXT2CODE��ʾ�� ԭ�ֶ�+Text �ϵ��ֵ�����ת��Ϊ ԭ�ֶ� �ϵ��ֵ���룬CODE2TEXT��ʾ�� ԭ�ֶ� �ϵ��ֵ���� ת��Ϊ ԭ�ֶ�+Text �ϵ��ֵ����� 

@Service
public class MemberService {
    @DictLoader(className = "com.mysql.jdbc.Driver", url = "jdbc:mysql://127.0.0.1:3306/test", username = "root", password = "123456")
    @DictReplace(direction = DictReplace.DIRECTION.TEXT2CODE)
    public boolean add(Member member) {
        System.out.println("�����û�" + member);
        return true;
    }
    @DictLoader(className = "com.mysql.jdbc.Driver", url = "jdbc:mysql://127.0.0.1:3306/test", username = "root", password = "123456")
    @DictReplace(direction = DictReplace.DIRECTION.TEXT2CODE)
    public boolean addList(List<Member> members) {
        System.out.println("�����û��б�" + members);
        return true;
    }
    @DictLoader(className = "com.mysql.jdbc.Driver", url = "jdbc:mysql://127.0.0.1:3306/test", username = "root", password = "123456")
    @DictReplace(direction = DictReplace.DIRECTION.CODE2TEXT)
    public Member query(Member member) {
        System.out.println("��ѯ�û�" + member);
        return member;
    }
    @DictLoader(className = "com.mysql.jdbc.Driver", url = "jdbc:mysql://127.0.0.1:3306/test", username = "root", password = "123456")
    @DictReplace(direction = DictReplace.DIRECTION.CODE2TEXT)
    public List<Member> queryList(List<Member> members) {
        System.out.println("��ѯ�û��б�" + members);
        return members;
    }
}