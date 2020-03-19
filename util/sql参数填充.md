## 把sql中的?替换成相应的参数，用于开发时快速定位问题

```java
public class StrReplaceDemo {
    boolean argMode = true;
    public static void main(String[] args) {
        String originString = "select * from users where createTime=?";
        String params = "2020/03/02(String)";


        doArgsReplace(originString, params);
    }

    private static void doArgsReplace(String originString, String params) {
        StringBuilder result = new StringBuilder();
        formatStringPlaceHolder(0, originString, result);
        Object[] paramList = getArgsFromString(params);
        String r = MessageFormat.format(result.toString().replaceAll("'", "''"), paramList);
        System.out.println(result);
        System.out.println(r);
    }

    private static Object[] getArgsFromString(String params) {
        List<Object> args = new ArrayList<>();
        String[] strings = params.split(",");
        for(String s : strings){
            s = s.trim();
            int i = s.lastIndexOf("(");
            if(i<0){
                args.add(s);
                continue;
            }
            String val = s.substring(0, i);
            if(s.substring(i,s.length()).equalsIgnoreCase("(String)")){
                args.add("'"+val+"'");
                continue;
            }
            args.add(val);
        }
        return args.toArray();
    }

    private static void formatStringPlaceHolder(int i, String originString, StringBuilder result) {
        int index = originString.indexOf("?");
        if(index<0){
            if(originString.length()>0){
                result.append(originString);
            }
            return;
        }
        String subItem = originString.substring(0, index);
        String nextItem = originString.substring(index+1, originString.length());

        result.append(subItem).append("{").append(i++).append("}");
        formatStringPlaceHolder(i, nextItem, result);
    }
}
```
