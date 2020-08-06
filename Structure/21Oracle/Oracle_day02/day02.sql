---��ͼ
---��ͼ�ĸ����ͼ�����ṩһ����ѯ�Ĵ��ڣ���������������ԭ��

---��ѯ��䴴����
create table emp as select * from scott.emp;
select * from emp;
---������ͼ��������dbaȨ�ޡ�
create view v_emp as select ename, job from emp;
---��ѯ��ͼ
select * from v_emp;
---�޸���ͼ[���Ƽ�]
update v_emp set job='CLERK' where ename='ALLEN';
commit;
---����ֻ����ͼ
create view v_emp1 as select ename, job from emp with read only;
---��ͼ�����ã�
---��һ����ͼ�������ε�һЩ�����ֶΡ�
---�ڶ�����֤�ܲ��ͷֲ����ݼ�ʱͳһ��


---����
--�����ĸ�����������ڱ�����Ϲ���һ��������
----�ﵽ�������߲�ѯЧ�ʵ�Ŀ�ģ�����������Ӱ����ɾ�ĵ�Ч�ʡ�
---��������
---������������
create index idx_ename on emp(ename);
---���������������������������������е�ԭʼֵ��
---���к�����ģ����ѯ������Ӱ�������Ĵ�����
select * from emp where ename='SCOTT'
---��������
---������������
create index idx_enamejob on emp(ename, job);
---���������е�һ��Ϊ���ȼ�����
---���Ҫ��������������������������ȼ������е�ԭʼֵ��
select * from emp where ename='SCOTT' and job='xx';---������������
select * from emp where ename='SCOTT' or job='xx';---����������
select * from emp where ename='SCOTT';---��������������


---pl/sql�������
---pl/sql��������Ƕ�sql���Ե���չ��ʹ��sql���Ծ��й��̻���̵����ԡ�
---pl/sql������Ա�һ��Ĺ��̻�������ԣ���������Ч��
---pl/sql���������Ҫ������д�洢���̺ʹ洢�����ȡ�

---��������
---��ֵ��������ʹ��:=Ҳ����ʹ��into��ѯ��丳ֵ
declare
    i number(2) := 10;
    s varchar2(10) := 'С��';
    ena emp.ename%type;---�����ͱ���
    emprow emp%rowtype;---��¼�ͱ���
begin
    dbms_output.put_line(i);
    dbms_output.put_line(s);
    select ename into ena from emp where empno = 7788;
    dbms_output.put_line(ena);
    select * into emprow from emp where empno = 7788;
    dbms_output.put_line(emprow.ename || '�Ĺ���Ϊ��' || emprow.job);
end;


---pl/sql�е�if�ж�
---����С��18�����֣����δ����
---�������18С��40�����֣����������
---�������40�����֣����������
declare
  i number(3) := &ii;
begin
  if i<18 then
    dbms_output.put_line('δ����');
  elsif i<40 then
    dbms_output.put_line('������');
  else
    dbms_output.put_line('������');
  end if;
end;

---pl/sql�е�loopѭ��
---�����ַ�ʽ���1��10�Ǹ�����
---whileѭ��
declare
  i number(2) := 1;
begin
  while i<11 loop
     dbms_output.put_line(i);
     i := i+1;
  end loop;  
end;
---exitѭ��
declare
  i number(2) := 1;
begin
  loop
    exit when i>10;
    dbms_output.put_line(i);
    i := i+1;
  end loop;
end;
---forѭ��
declare

begin
  for i in 1..10 loop
     dbms_output.put_line(i);  
  end loop;
end;

---�α꣺���Դ�Ŷ�����󣬶��м�¼��
---���emp��������Ա��������
declare
  cursor c1 is select * from emp;
  emprow emp%rowtype;
begin
  open c1;
     loop
         fetch c1 into emprow;
         exit when c1%notfound;
         dbms_output.put_line(emprow.ename);
     end loop;
  close c1;
end;

-----��ָ������Ա���ǹ���
declare
  cursor c2(eno emp.deptno%type) 
  is select empno from emp where deptno = eno;
  en emp.empno%type;
begin
  open c2(10);
     loop
        fetch c2 into en;
        exit when c2%notfound;
        update emp set sal=sal+100 where empno=en;
        commit;
     end loop;  
  close c2;
end;
----��ѯ10�Ų���Ա����Ϣ
select * from emp where deptno = 10;


---�洢����
--�洢���̣��洢���̾�����ǰ�Ѿ�����õ�һ��pl/sql���ԣ����������ݿ��
--------����ֱ�ӱ����á���һ��pl/sqlһ�㶼�ǹ̶������ҵ��
----��ָ��Ա����100��Ǯ
create or replace procedure p1(eno emp.empno%type)
is

begin
   update emp set sal=sal+100 where empno = eno;
   commit;
end;

select * from emp where empno = 7788;
----����p1
declare

begin
  p1(7788);
end;

----ͨ���洢����ʵ�ּ���ָ��Ա������н
----�洢���̺ʹ洢�����Ĳ��������ܴ�����
----�洢�����ķ���ֵ���Ͳ��ܴ�����
create or replace function f_yearsal(eno emp.empno%type) return number
is
  s number(10);     
begin
  select sal*12+nvl(comm, 0) into s from emp where empno = eno;
  return s;
end;

----����f_yearsal
----�洢�����ڵ��õ�ʱ�򣬷���ֵ��Ҫ���ա�
declare
  s number(10); 
begin
  s := f_yearsal(7788);
  dbms_output.put_line(s);
end;

---out���Ͳ������ʹ��
---ʹ�ô洢����������н
create or replace procedure p_yearsal(eno emp.empno%type, yearsal out number)
is
   s number(10);
   c emp.comm%type;
begin
   select sal*12, nvl(comm, 0) into s, c from emp where empno = eno;
   yearsal := s+c;
end;

---����p_yearsal
declare
  yearsal number(10);
begin
  p_yearsal(7788, yearsal);
  dbms_output.put_line(yearsal);
end;

----in��out���Ͳ�����������ʲô��
---�����漰��into��ѯ��丳ֵ����:=��ֵ�����Ĳ�����������ʹ��out�����Ρ�


---�洢���̺ʹ洢����������
---�﷨���𣺹ؼ��ֲ�һ����
------------�洢�����ȴ洢���̶�������return��
---�������𣺴洢�����з���ֵ�����洢����û�з���ֵ��
----------����洢������ʵ���з���ֵ��ҵ�����Ǿͱ���ʹ��out���͵Ĳ�����
----------�����Ǵ洢����ʹ����out���͵Ĳ���������Ҳ����������˷���ֵ��
----------�����ڴ洢�����ڲ���out���Ͳ�����ֵ����ִ����Ϻ�����ֱ���õ�������Ͳ�����ֵ��

----���ǿ���ʹ�ô洢�����з���ֵ�����ԣ����Զ��庯����
----���洢���̲��������Զ��庯����
----�������󣺲�ѯ��Ա��������Ա�����ڲ������ơ�
----����׼����������scott�û��µ�dept���Ƶ���ǰ�û��¡�
create table dept as select * from scott.dept;
----ʹ�ô�ͳ��ʽ��ʵ�ְ�������
select e.ename, d.dname
from emp e, dept d
where e.deptno=d.deptno;
----ʹ�ô洢������ʵ���ṩһ�����ű�ţ����һ���������ơ�
create or replace function fdna(dno dept.deptno%type) return dept.dname%type
is
  dna dept.dname%type;
begin
  select dname into dna from dept where deptno = dno;
  return dna;
end;
---ʹ��fdna�洢������ʵ�ְ������󣺲�ѯ��Ա��������Ա�����ڲ������ơ�
select e.ename, fdna(e.deptno)
from emp e;


---�������������ƶ�һ����������������ɾ�Ĳ�����ʱ��
----ֻҪ����ù����Զ�������������á�
----��伶����������������for each row�Ĵ�������
----�м���������������for each row�ľ����м���������
-----------��for each row��Ϊ��ʹ��:old����:new�������һ�м�¼��

---��伶������
----����һ����¼�����һ����Ա����ְ
create or replace trigger t1
after
insert
on person
declare

begin
  dbms_output.put_line('һ����Ա����ְ');
end;
---����t1
insert into person values (1, 'С��');
commit;
select * from person;

---�м��𴥷���
---���ܸ�Ա����н
---raise_application_error(-20001~-20999֮��, '������ʾ��Ϣ');
create or replace trigger t2
before
update
on emp
for each row
declare

begin
  if :old.sal>:new.sal then
     raise_application_error(-20001, '���ܸ�Ա����н');
  end if;
end;
----����t2
select * from emp where empno = 7788;
update emp set sal=sal-1 where empno = 7788;
commit;


----������ʵ���������������м���������
---���������û������������֮ǰ���õ�������������ݣ�
------���������е������и�ֵ��
create or replace trigger auid
before
insert
on person
for each row
declare

begin
  select s_person.nextval into :new.pid from dual;
end;
--��ѯperson������
select * from person;
---ʹ��auidʵ����������
insert into person (pname) values ('a');
commit;
insert into person values (1, 'b');
commit;


----oracle10g    ojdbc14.jar
----oracle11g    ojdbc6.jar









