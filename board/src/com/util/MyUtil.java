//�Խ��� ����¡ ó��

//check~!!!
//���� ���� Ȯ���غ����� �ϴ� ����¡ ó�� �����
//�پ��� ����� �� �Ѱ���(�׳��� ���� ���� ���...) �̴�.
//�н��� ��ģ ���Ŀ�... ��~!!! �߰������� ������ �����ϰ�
//Ȯ���Ű��, �ٸ� ������ ã�ƺ��� �����ؾ� �Ѵ�!!!!

package com.util;

public class MyUtil
{
	//(1) ��ü ������ ���� ���ϴ� �޼ҵ�
	//numPerPage : �� �������� ǥ���� ������(�Խù�)�� ��
	//dataCount : ��ü ������(�Խù�) ��
	public int getPageCount(int numPerPage, int dataCount)
	{
		int pageCount = 0;
		
		pageCount = dataCount/numPerPage;  //���� ������ ����
		
		if(dataCount % numPerPage != 0)
			pageCount++;
		
		
		return pageCount;
		
	}
	//�� �������� 10���� �Խù��� ����� ��
	//�� 32���� �Խù��� �������� �����ϱ� ���ؼ���
	// 32 / 10 �� ������ �����Ͽ� ��� 3 �� ���� �� �ִ�.
	// -> 'pageCount = dataCount/numPerPage;'
	//�׷��� �̶�, ������ 2���� �Խù��� ����� �ֱ� ���ؼ���
	//������ �ϳ��� �� �ʿ��ϴ�.
	// -> pageCount++;
	
	
	//(2) ����¡ ó�� ����� �޼ҵ�
	//currentPage = ���� ǥ���� ������
	//totalpage = ��ü ������ ��
	//listUrl = ��ũ�� ������ url
	public String pageIndexList(int currentPage, int totalPage, String listUrl)
	{
		//���� ����¡�� ������ StringBuffer ����
		StringBuffer strList = new StringBuffer();
		
		int numPerBlock = 10;
		//--����¡ ó�� �� �Խù� ����Ʈ �ϴ��� ���ڸ� 10���� �����ְڴ�.
		
		int currentPageSetup;
		//--���� ������(�� �������� �������� �����ִ� ���ڰ� �޶����� �ϱ� ����...)
		
		int page;
		int n;
		//--���� ������ ���� ���� ó������ �̵��ϱ� ���� ����
		//  (�󸶸�ŭ �̵��ؾ� �ϴ���...)
		
		
		// ����¡ ó���� ������ �ʿ����� ���� ���
		//--�����Ͱ� �������� �ʰų� �������� ���� 
		//  1�������� ��ä��� ���� ������ ����¡ ó���� �� �ʿ䰡 ����.
		if (currentPage==0)
			return "";
		
		
		//�� ������ ��û�� ó���ϴ� ��������
		//url ����� ���Ͽ� ���� ó��
		/*
		 - Ŭ���̾�Ʈ ��û�� ���� -> List.jsp -> (����) -> List.jsp + '?' + pageNum=1
		 
		 - Ŭ���̾�Ʈ ��û�� ���� -> List.jsp?subject=study -> (����) -> List.jsp?subject=study + '&' + pageNum=1
		 
		  */
		
	
		//��ũ�� ������ url�� ���� ������ ó��~!!!
		if (listUrl.indexOf("?") != -1 )   //��ũ�� ������ url �� ? �� ���������...
		{
			listUrl = listUrl + "&";   //listUrl += "&";
		}
		else							//��ũ�� ������ URL �� '?' �� ������...
		{
			listUrl = listUrl + "?";   //listUrl += "?";
		}
		//���� ���, �˻����� �����ϸ�
		//�̹� request ���� searchKey ���� searchValue ���� ����ִ� ��Ȳ�̹Ƿ�
		//'&' �� �ٿ��� �Ӽ����� �߰��� �־�� �Ѵ�.
		
		//currentPageSetup = ǥ���� ù ������ -1
		currentPageSetup = (currentPage / numPerBlock) * numPerBlock;
		//���� ���� �������� 5�������̰�(currentPage=5)
		//����Ʈ �ϴܿ� ������ ������ ������ 10�̸�(numPerBlock=10)
		//'5 / 10 = 0' �̸�... ���⿡ ' * 10 ' (10 �� ���ص�) 0 �̴�.
		//������, ���� �������� 11���������(currentPage=11)
		//' 11 / 10 = 1' �̸�... ���⿡ ' * 10 ' (10 �� ���ϸ�) 10 �̴�.
		//�׷���... currentPageSetup �� 10 �� �Ǵ� ���̴�.
		
		//30�������� �ӹ��� �־ Ŀ��Ʈ �������¾��� 20���� ���;� �Ѵ�.
		//�׷��� 31�������� ���� Ŀ��Ʈ �������¾��� 30���� ��������...
		
		//10�� ����� �� ������ �߻�
		if (currentPage % numPerBlock == 0)
		{
			currentPageSetup = currentPageSetup - numPerBlock;
			//currentPageSetup -= numPerBlock;
		}
		//���� �� ó������... (���� 90)
		//���� �������� 20���������ٸ� (currentPage = 20)
		//' 20 / 10 = 2 ' �̸�... ���⿡ ' = 10 ' (10�� ���ؼ�) 20�� �Ǵµ�
		//�̿� ���� ��Ȳ�̶��... �ٽ� 10�� ���� 10 ���� ������ֱ� ���� ����.
		
		
		
		//1�������� ���Բ�(�� ó������)
		//��ü ��Ż�������� 
		if((totalPage>numPerBlock) && (currentPageSetup>0))
		{
			strList.append("<a href='" + listUrl + "pageNum=1'>1</a>");
		}
		//-- listUrl �� ������ (���� 77) �̹� ��ó���� ���� ��Ȳ�̱� ������ 
		//'...?' ���� �Ǵ� '...?...&'�� �����̴�.
		//�̷� ���� �����
		//'...?pageNum=1' �̰ų� '...&pageNum=1' �� �Ǵ� ��Ȳ�̴�.
		
		
		
		
		//&nbsp;
		//a �±� �տ��ٰ� ���� �Ϻη� ����
		
		
		
		
		
		//Prev(��������)
		n = currentPage - numPerBlock;
		// n -> �ش� ��������ŭ ������ ���� ���� ����
		
		if ( (totalPage>numPerBlock) && (currentPageSetup>0) )
		{
			strList.append(" <a href='" + listUrl + "pageNum=" + n + "'>Prev</a>");
		}
		//-- currentPageSetup �� 0 ���� ū ����
		//   �̹� �������� 11 �̻��̶�� �ǹ��̸�
		//   �� ��, ���� ������(currentPage)�� 11 �̻��� ���
		//   'Prev' �� ���̱� ���� ����,
		//-- 'Prev'�� Ŭ���� ���
		//    n ���� �������� �̵��ϴµ�
		//    12 ���� 'Prev' �� ��� 2 �������� �ǰ�,
		//    22 ���� 'Prev' �� ��� 12 �������� �� �� �ֵ��� ó���ϴ� ����̴�.
		
		
		
		
		
		//�� ������ �ٷΰ���(�� ������ �ٷΰ���)
		page = currentPageSetup + 1;
		//-- '+1' �� �����ϴ� ������
		//   �տ��� currentPageSetup ���� 10�� �����Դٸ�
		//   10���� �����ϴ� ���� �ƴ϶�
		//   �ٷΰ��� �������� 11���� �����ؾ� �ϱ� �����̴�.
		
		while ((page<=totalPage) && (page<=currentPageSetup+numPerBlock))
		{
			if (page==currentPage)
			{
				strList.append(" <span style='color:orange; font-weight:bold;'>" +  page + "</span>");
			}
			else
			{
				strList.append(" <a href='" + listUrl + "pageNum=" + page + "'>" + page + "</a>");
			}
			
			page++;
		}
		//���� �� ���������� ��ũ�� �ɷ������� �ȵȴ�.
		//�׷��� span ���� ó��
		//�ݺ��� ���� �������� �ϳ��� �����ϴµ�
		//������ �������� 
		//������ ��ó���� �ɷ� url ó��
		
		
		
		
		//Next(��������)
		n = currentPage + numPerBlock;
		if ((totalPage-currentPageSetup) > numPerBlock)
		{
			strList.append(" <a href='" + listUrl + "pageNum=" + n + "'>Next</a>");
		}
		
		
		
		//������ ������(�� ����������)
		if( (totalPage>numPerBlock) && (currentPageSetup+numPerBlock)<totalPage)
		{
			strList.append(" <a href='" + listUrl + "pageNum=" + totalPage + "'>" + totalPage + "</a>");
		}
		
		
		
		return strList.toString();
	}
	
	
	
	
	
	
	
	
}
