# ProMeet ? ������Ʈ ���� �ַ�� (MVC Model2)

## ������Ʈ �Ұ�
- **�Ⱓ**: 9/22 ~ 10/17
- **��Ű��ó**: MVC Model2 (Servlet/JSP, JSTL/EL)
- **����/�����̳�**: Apache Tomcat
- **DB ����**: MyBatis Framework (JDBC ���� ��� ����)

### �� ����
- **����**: �ڼ���
- **����**: ������, ������, �����

### ����(�о�)
- ȸ�翡�� �����ϴ� ������Ʈ�� �����ϴ� **������Ʈ ���� �ַ��**

### Ÿ�� ȸ��
- **�ַ��(����, ��û)** �� **���� ������Ʈ**�� �����ϴ� ȸ��

### �丣�ҳ� (Actor: �� ȸ���� ������Ʈ ����)
- ȸ�翡�� ������Ʈ�� ���� �Ǵ� ��û���� �����Ͽ� �����Ѵ�.
- ���� ������Ʈ�� �� ������ ������ **�����ϰ� ���ߵ� ���� �÷���**�� ���Ѵ�.
- ������Ʈ ���࿡�� ������ �� �ֵ��� **�ʼ� ��ɸ� ��� �ַ��**�� ��ȣ�Ѵ�.

### ��ġ��ŷ
- **������ + �÷ο�(Flow)**, �� �� ���� �ַ��

### �ٽ� ���
- **MVC Model2** ������ ���� ����
- **Tomcat** ���� �����̳� ���
- **MyBatis** Framework�� SQL/Ʈ����� �и�

---

## ��Ű��ó ���̾�׷�
![Architecture](<docs/promeet_architecture.jpg)

## ERD
![ERD](docs/promeet_erd.jpg)

---

### Conventional Commits ? ���� ���̵�

#### ����
    <type>(<scope>): <subject>

- **type**: ���� ����  
- **scope**(����): ���/����  
- **subject**: �� �� ���(��������������, ��ħǥ X)

#### ���� ���� type
| type | �ǹ� | ���� |
|---|---|---|
| feat | ��� �߰� | `feat(tasks): ����� �ϰ� ���� �߰�` |
| fix | ���� ���� | `fix(mapper): ORA-00923 ���� ���� ����` |
| refactor | �����͸�(���� ����) | `refactor(service): �ߺ� ���� ����` |
| docs | ���� ���� | `docs(readme): ��ġ ���̵� ����` |
| style | ����/��Ÿ��(���� ����) | `style(css): ǥ �ణ ����` |
| test | �׽�Ʈ | `test(dao): ������Ʈ ��� ���̽� �߰�` |
| build | ����/������ | `build(gradle): mybatis ���� ������Ʈ` |
| chore | ����/ȯ�� | `chore: .gitignore ����` |
| perf | ���� ���� | `perf(sql): �ε��� �߰��� ��ȸ ����ȭ` |
| revert | Ŀ�� �ǵ��� | `revert: "feat: �α��� �߰�"` |

#### ���� ���� scope
`frontend`, `backend`, `dao`, `service`, `mapper`, `controller`, `jsp`, `css`, `js`, `projects`, `tasks`, `schedules`, `auth`, `config`, `core`, `infra`

#### subject ��Ģ
- **��������������**, **50�� ����**, **��ħǥ X**  
  - ��) `������Ʈ ��� ������ ���� ���� ���� �߰�`


#### ����
    feat(projects): ����/������ ��� ���� ����
    - ���� ������Ʈ ���� ���� �߰�
    - �����뿡���� total/done/dday ���� ����


    fix(mapper): trailing comma ���ŷ� ORA-00923 �ذ�


    chore: .gitignore�� /captures, /build �߰�



# ProMeet HTMLs

## �α���
<sub>login.html</sub>  
![Login Page](WebContent/captures/login.png)

## ����������
<sub>myPage.html</sub>  
![My Page](WebContent/captures/myPage.png)

## ������Ʈ ����
<sub>projects.html</sub>  
![Projects](WebContent/captures/projects.png)

## ������Ʈ ������
<sub>projectBin.html</sub>  
![Project Bin](WebContent/captures/projectBin.png)

## ������Ʈ �߰�
<sub>projectAdd.html</sub>  
![Project Add](WebContent/captures/projectAdd.png)

## ������Ʈ ����
<sub>projectUpdate.html</sub>  
![Project Update](WebContent/captures/projectUpdate.png)

## ����/������ ������Ʈ
<sub>projectTerminated.html</sub>  
![Project Terminated](WebContent/captures/projectTerminated.png)

## ���� ����
<sub>tasks.html</sub>  
![Tasks](WebContent/captures/tasks.png)

## ���� �߰�/����
<sub>taskUpdate.html</sub>  
![Task Update](WebContent/captures/taskUpdate.png)

## ������Ʈ ���
<sub>projectMembers.html</sub>  
![Project Members](WebContent/captures/projectMembers.png)

## ���� ���
<sub>taskMembers.html</sub>  
![Task Members](WebContent/captures/taskMembers.png)

## �� ������
<sub>details.html</sub>  
![Details](WebContent/captures/details.png)

## �� ����
<sub>detailUpdate.html</sub>  
![Detail Update](WebContent/captures/detailUpdate.png)

## ������ ����
<sub>fileBoxMain.html</sub>  
![File Box Main](WebContent/captures/fileBoxMain.png)

## ������ ����
<sub>foldersInProject.html</sub>  
![Folders in Project](WebContent/captures/foldersInProject.png)

## ���� �� ���� ����Ʈ
<sub>filesInTask.html</sub>  
![Files in Task](WebContent/captures/filesInTask.png)

## ���� �˻�
<sub>fileSearchResult.html</sub>  
![File Search Result](WebContent/captures/fileSearchResult.png)

## ���� ����
<sub>schedule.html</sub>  
![Schedule](WebContent/captures/schedule.png)

## ���� �߰�
<sub>scheduleAdd.html</sub>  
![Schedule Add](WebContent/captures/scheduleAdd.png)

## ���� ����
<sub>scheduleUpdate.html</sub>  
![Schedule Update](WebContent/captures/scheduleUpdate.png)
