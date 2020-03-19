```java

private static void commonSqlFileParser() {
        String readStr = Futil.readStr("D:/workspace/java/txt4regex.txt");
        String regex = "^(?:INSERT INTO|UPDATE|SELECT|WITH|DELETE)(?:[^;']|(?:'[^']+'))+;\\s*$";
        Pattern compile = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = compile.matcher(readStr);
        int count = 0;
        while (matcher.find()) {
            System.out.println((++count) + ": ");
            System.out.println(matcher.group());
            System.out.println("===========================================");
        }
    }

    private static void oracleSqlFileParser() {
        String readStr = Futil.readStr("C:/Users/c84112937/Desktop/高斯切换/1-MDF/MDF-DDL/PRMCOMM.sql");
        // String pattern = "(?m)(?s)(?i)^(?<sql>)$"; //
        // String pattern = "^(?<sql>)$"; //
        String pattern = "^(?<sql>(?:--\\s*)?ALTER\\s+TABLE.+?;)$"; // alter table(check,primarykey,unique,foreignkey)
        // String pattern = "^(?<sql>(--\\s*)?CREATE\\s+?(?:(?:UNIQUE|BITMAP)\\s+?)?INDEX.+?;)$"; // create index.
        // String pattern = "^(?<sql>(DROP\\s+SEQUENCE.+?;\\s+)?CREATE\\s+SEQUENCE.+?;)$"; // create sequence
        // String pattern = "^(?<sql>CREATE\\s+(OR\\s+REPLACE\\s+)?function.+?/)$"; // create function
        // String pattern = "^(?<sql>CREATE\\s+?(OR\\s+?REPLACE\\s+?)?PROCEDURE.+?/)$"; //create procedure
        // String pattern = "^(?<sql>CREATE\\s+?(or\\s+?replace\\s+?)?view.+?;)$"; // create view
        // String pattern = "^(?<sql>COMMENT ON TABLE.+?;)$"; // add table field comment.
        // String pattern = "^(?<sql>(DROP\\s+?TABLE.+?;\\s+?)?CREATE\\s+?TABLE.+?;)$"; // create table
        Pattern compile = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = compile.matcher(readStr);
        System.out.println("groupCount: " + matcher.groupCount());
        int count = 0;
        while (matcher.find()) {
            System.out.println((++count) + ": ");
            System.out.println(matcher.group("sql"));
            System.out.println("===========================================");
        }
    }
```
