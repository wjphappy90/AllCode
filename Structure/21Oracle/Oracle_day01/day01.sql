--������ռ�
create tablespace itheima
datafile 'c:\itheima.dbf'
size 100m
autoextend on
next 10m;
--ɾ����ռ�
drop tablespace itheima;

--�����û�
create user itheima
identified by itheima
default tablespace itheima;

--���û���Ȩ
--oracle���ݿ��г��ý�ɫ
connect--���ӽ�ɫ��������ɫ
resource--�����߽�ɫ
dba--��������Ա��ɫ
--��itheima�û�����dba��ɫ
grant dba to itheima;

---�л���itheima�û���

---����һ��person��
create table person(
       pid number(20),
       pname varchar2(10)
);

---�޸ı�ṹ
---���һ��
alter table person add (gender number(1));
---�޸�������
alter table person modify gender char(1);
---�޸�������
alter table person rename column gender to sex;
---ɾ��һ��
alter table person drop column sex;

---��ѯ���м�¼
select * from person;
----���һ����¼
insert into person (pid, pname) values (1, 'С��');
commit;
----�޸�һ����¼
update person set pname = 'С��' where pid = 1;
commit;

----����ɾ��
--ɾ������ȫ����¼
delete from person;
--ɾ����ṹ
drop table person;
--��ɾ�����ٴδ�����Ч����ͬ��ɾ������ȫ����¼��
--���������������£������ڱ��д�������������£��ò���Ч�ʸߡ�
--���������ṩ��ѯЧ�ʣ����ǻ�Ӱ����ɾ��Ч�ʡ�
truncate table person;


----���в���������κ�һ�ű����ǿ����߼��ͱ����󶨡�
----���У�Ĭ�ϴ�1��ʼ�����ε�������Ҫ������������ֵʹ�á�
----dual�����ֻ��Ϊ�˲�ȫ�﷨��û���κ����塣
create sequence s_person;
select s_person.nextval from dual;
----���һ����¼
insert into person (pid, pname) values (s_person.nextval, 'С��');
commit;
select * from person;

----scott�û�������tiger��
--����scott�û�
alter user scott account unlock;
--����scott�û������롾�˾�Ҳ���������������롿
alter user scott identified by tiger;
--�л���scott�û���

--���к�����������һ�У�����һ��ֵ��
---�ַ�����
select upper('yes') from dual;--YES
select lower('YES') from dual;--yes
----��ֵ����
select round(56.16, -2) from dual;---�������룬����Ĳ�����ʾ������λ��
select trunc(56.16, -1) from dual;---ֱ�ӽ�ȡ�����ڿ�����λ���������Ƿ����5.
select mod(10, 3) from dual;---������
----���ں���
----��ѯ��emp��������Ա����ְ�������ڼ��졣
select sysdate-e.hiredate from emp e;
----�������˿�
select sysdate+1 from dual;
----��ѯ��emp��������Ա����ְ�������ڼ��¡�
select months_between(sysdate,e.hiredate) from emp e;
----��ѯ��emp��������Ա����ְ�������ڼ��ꡣ
select months_between(sysdate,e.hiredate)/12 from emp e;
----��ѯ��emp��������Ա����ְ�������ڼ��ܡ�
select round((sysdate-e.hiredate)/7) from emp e;
----ת������
---����ת�ַ���
select to_char(sysdate, 'fm yyyy-mm-dd hh24:mi:ss') from dual;
---�ַ���ת����
select to_date('2018-6-7 16:39:50', 'fm yyyy-mm-dd hh24:mi:ss') from dual;
----ͨ�ú���
---���emp��������Ա������н
----����������nullֵ�����nullֵ�������������������㣬�������null��
select e.sal*12+nvl(e.comm, 0) from emp e;

---�������ʽ
---�������ʽ��ͨ��д����mysql��oracleͨ��
---��emp����Ա����������
select e.ename, 
       case e.ename
         when 'SMITH' then '����'
           when 'ALLEN' then '�����'
             when 'WARD' then '���С��'
               --else '����'
                 end
from emp e;
---�ж�emp����Ա�����ʣ��������3000��ʾ�����룬�������1500����3000��ʾ�е����룬
-----������ʾ������
select e.sal, 
       case 
         when e.sal>3000 then '������'
           when e.sal>1500 then '�е�����'
               else '������'
                 end
from emp e;
----oracle�г�������������õ����š�
----oracleר���������ʽ
select e.ename, 
        decode(e.ename,
          'SMITH',  '����',
            'ALLEN',  '�����',
              'WARD',  '���С��',
                '����') "������"             
from emp e;

--���к������ۺϺ������������ڶ��У�����һ��ֵ��
select count(1) from emp;---��ѯ������
select sum(sal) from emp;---�����ܺ�
select max(sal) from emp;---�����
select min(sal) from emp;---��͹���
select avg(sal) from emp;---ƽ������


---�����ѯ
---��ѯ��ÿ�����ŵ�ƽ������
---�����ѯ�У�������group by�����ԭʼ�У����ܳ�����select����
---û�г�����group by������У�����select���棬������ϾۺϺ�����
---�ۺϺ�����һ�����ԣ����԰Ѷ��м�¼���һ��ֵ��
select e.deptno, avg(e.sal)--, e.ename
from emp e
group by e.deptno;
---��ѯ��ƽ�����ʸ���2000�Ĳ�����Ϣ
select e.deptno, avg(e.sal) asal
from emp e
group by e.deptno
having avg(e.sal)>2000;
---��������������ʹ�ñ������жϡ�
--����������������Ҳ����ʹ�ñ���������
select ename, sal s from emp where sal>1500;

---��ѯ��ÿ�����Ź��ʸ���800��Ա����ƽ������
select e.deptno, avg(e.sal) asal
from emp e
where e.sal>800
group by e.deptno;
----where�ǹ��˷���ǰ�����ݣ�having�ǹ��˷��������ݡ�
---������ʽ��where������group by֮ǰ��having����group by֮��
---��ѯ��ÿ�����Ź��ʸ���800��Ա����ƽ������
---Ȼ���ٲ�ѯ��ƽ�����ʸ���2000�Ĳ���
select e.deptno, avg(e.sal) asal
from emp e
where e.sal>800
group by e.deptno
having avg(e.sal)>2000;


---����ѯ�е�һЩ����
---�ѿ�����
select *
from emp e, dept d;
---��ֵ����
select *
from emp e, dept d
where e.deptno=d.deptno;
---������
select *
from emp e inner join dept d
on e.deptno = d.deptno;
---��ѯ�����в��ţ��Լ������µ�Ա����Ϣ���������ӡ�
select *
from emp e right join dept d
on e.deptno=d.deptno;
---��ѯ����Ա����Ϣ���Լ�Ա����������
select *
from emp e left join dept d
on e.deptno=d.deptno;
---oracle��ר��������
select *
from emp e, dept d
where e.deptno(+) = d.deptno;

select * from emp;
---��ѯ��Ա��������Ա���쵼����
---�����ӣ���������ʵ����վ�ڲ�ͬ�ĽǶȰ�һ�ű��ɶ��ű�
select e1.ename, e2.ename
from emp e1, emp e2
where e1.mgr = e2.empno;
------��ѯ��Ա��������Ա���������ƣ�Ա���쵼������Ա���쵼��������
select e1.ename, d1.dname, e2.ename, d2.dname
from emp e1, emp e2, dept d1, dept d2
where e1.mgr = e2.empno
and e1.deptno=d1.deptno
and e2.deptno=d2.deptno;

---�Ӳ�ѯ
---�Ӳ�ѯ����һ��ֵ
---��ѯ�����ʺ�SCOTTһ����Ա����Ϣ
select * from emp where sal in
(select sal from emp where ename = 'SCOTT')
---�Ӳ�ѯ����һ������
---��ѯ�����ʺ�10�Ų�������Ա��һ����Ա����Ϣ
select * from emp where sal in
(select sal from emp where deptno = 10);
---�Ӳ�ѯ����һ�ű�
---��ѯ��ÿ��������͹��ʣ�����͹���Ա���������͸�Ա�����ڲ�������
---1���Ȳ�ѯ��ÿ��������͹���
select deptno, min(sal) msal
from emp 
group by deptno;
---2���������飬�õ����ս����
select t.deptno, t.msal, e.ename, d.dname
from (select deptno, min(sal) msal
      from emp 
      group by deptno) t, emp e, dept d
where t.deptno = e.deptno
and t.msal = e.sal
and e.deptno = d.deptno;


----oracle�еķ�ҳ
---rownum�кţ���������select������ʱ��
--ÿ��ѯ��һ�м�¼���ͻ��ڸ����ϼ���һ���кţ�
--�кŴ�1��ʼ�����ε��������������ߡ�

----���������Ӱ��rownum��˳��
select rownum, e.* from emp e order by e.sal desc
----����漰�����򣬵��ǻ�Ҫʹ��rownum�Ļ������ǿ����ٴ�Ƕ�ײ�ѯ��
select rownum, t.* from(
select rownum, e.* from emp e order by e.sal desc) t;


----emp���ʵ������к�ÿҳ������¼����ѯ�ڶ�ҳ��
----rownum�кŲ���д�ϴ���һ��������
select * from(
    select rownum rn, tt.* from(
          select * from emp order by sal desc
    ) tt where rownum<11
) where rn>5




























