这是一个用于字典替换的项目，通过mvn package打成jar包后引入项目即可使用

1.使用注解 @Dict 在实体类中标识需要字典转换的字段，其中dictTable为表名，dictType、dictCode、dictText为表中字典类型、字典编码、字典名称对应的字段名，dictTypeValue为本字段的类型名称，dictTextSuffix为字典名称的存放字段后缀（要求为原字段名+后缀名）

public class Member {
    @Dict(dictTable = "dict", dictType = "type", dictCode = "code", dictText = "text", dictTypeValue = "name", dictTextSuffix = "Text")
    private String username;
    private String usernameText;
}

2.使用注解 @DictLoader 在服务层标识需要切面的方法，其中className为数据库驱动，url为数据库地址，username为数据库用户名，password为数据库密码。同时使用注解 @DictReplace 标识字典替换的方向，TEXT2CODE表示将 原字段+Text 上的字典名称转化为 原字段 上的字典编码，CODE2TEXT表示将 原字段 上的字典编码 转化为 原字段+Text 上的字典名称 

@Service
public class MemberService {
    @DictLoader(className = "com.mysql.jdbc.Driver", url = "jdbc:mysql://127.0.0.1:3306/test", username = "root", password = "123456")
    @DictReplace(direction = DictReplace.DIRECTION.TEXT2CODE)
    public boolean add(Member member) {
        System.out.println("新增用户" + member);
        return true;
    }
    @DictLoader(className = "com.mysql.jdbc.Driver", url = "jdbc:mysql://127.0.0.1:3306/test", username = "root", password = "123456")
    @DictReplace(direction = DictReplace.DIRECTION.TEXT2CODE)
    public boolean addList(List<Member> members) {
        System.out.println("新增用户列表" + members);
        return true;
    }
    @DictLoader(className = "com.mysql.jdbc.Driver", url = "jdbc:mysql://127.0.0.1:3306/test", username = "root", password = "123456")
    @DictReplace(direction = DictReplace.DIRECTION.CODE2TEXT)
    public Member query(Member member) {
        System.out.println("查询用户" + member);
        return member;
    }
    @DictLoader(className = "com.mysql.jdbc.Driver", url = "jdbc:mysql://127.0.0.1:3306/test", username = "root", password = "123456")
    @DictReplace(direction = DictReplace.DIRECTION.CODE2TEXT)
    public List<Member> queryList(List<Member> members) {
        System.out.println("查询用户列表" + members);
        return members;
    }
}