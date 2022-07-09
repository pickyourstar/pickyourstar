//게시판 페이징 처리

//check~!!!
//지금 같이 확인해보고자 하는 페이징 처리 기법은
//다양한 방법들 중 한가지(그나마 쉬운 것을 골라...) 이다.
//학습을 마친 이후에... 꼭~!!! 추가적으로 개념을 정리하고
//확장시키고, 다른 방법들고 찾아보고 공부해야 한다!!!!

package com.util;

public class MyUtil
{
	//(1) 전체 페이지 수를 구하는 메소드
	//numPerPage : 한 페이지에 표시할 데이터(게시물)의 수
	//dataCount : 전체 데이터(게시물) 수
	public int getPageCount(int numPerPage, int dataCount)
	{
		int pageCount = 0;
		
		pageCount = dataCount/numPerPage;  //정수 나누기 정수
		
		if(dataCount % numPerPage != 0)
			pageCount++;
		
		
		return pageCount;
		
	}
	//한 페이지에 10개의 게시물을 출력할 때
	//총 32개의 게시물을 페이지로 구성하기 위해서는
	// 32 / 10 의 연산을 수행하여 결과 3 을 얻을 수 있다.
	// -> 'pageCount = dataCount/numPerPage;'
	//그런데 이때, 나머지 2개의 게시물을 출력해 주기 위해서는
	//페이지 하나가 더 필요하다.
	// -> pageCount++;
	
	
	//(2) 페이징 처리 기능의 메소드
	//currentPage = 현재 표시할 페이지
	//totalpage = 전체 페이지 수
	//listUrl = 링크를 설정할 url
	public String pageIndexList(int currentPage, int totalPage, String listUrl)
	{
		//실제 페이징을 저장한 StringBuffer 변수
		StringBuffer strList = new StringBuffer();
		
		int numPerBlock = 10;
		//--페이징 처리 시 게시물 리스트 하단의 숫자를 10개씩 보여주겠다.
		
		int currentPageSetup;
		//--현재 페이지(이 페이지를 기준으로 보여주는 숫자가 달라져야 하기 때문...)
		
		int page;
		int n;
		//--이전 페이지 블럭과 같은 처리에서 이동하기 위한 변수
		//  (얼마만큼 이동해야 하는지...)
		
		
		// 페이징 처리가 별도로 필요하지 않은 경우
		//--데이터가 존재하지 않거나 데이터의 수가 
		//  1페이지도 못채우는 경우는 별도로 페이징 처리를 할 필요가 없다.
		if (currentPage==0)
			return "";
		
		
		//※ 페이지 요청을 처리하는 과정에서
		//url 경로의 패턴에 대한 처리
		/*
		 - 클라이언트 요청의 형태 -> List.jsp -> (가공) -> List.jsp + '?' + pageNum=1
		 
		 - 클라이언트 요청의 형태 -> List.jsp?subject=study -> (가공) -> List.jsp?subject=study + '&' + pageNum=1
		 
		  */
		
	
		//링크를 설정할 url에 대한 선가공 처리~!!!
		if (listUrl.indexOf("?") != -1 )   //링크를 설정할 url 에 ? 가 들어있으면...
		{
			listUrl = listUrl + "&";   //listUrl += "&";
		}
		else							//링크를 설정할 URL 에 '?' 가 없으면...
		{
			listUrl = listUrl + "?";   //listUrl += "?";
		}
		//예를 들어, 검색값이 존재하면
		//이미 request 값에 searchKey 값과 searchValue 값이 들어있는 상황이므로
		//'&' 를 붙여서 속성값을 추가해 주어야 한다.
		
		//currentPageSetup = 표시할 첫 페이지 -1
		currentPageSetup = (currentPage / numPerBlock) * numPerBlock;
		//만약 현재 페이지가 5페이지이고(currentPage=5)
		//리스트 하단에 보여줄 페이지 갯수가 10이면(numPerBlock=10)
		//'5 / 10 = 0' 이며... 여기에 ' * 10 ' (10 을 곱해도) 0 이다.
		//하지만, 현재 페이지가 11페이지라면(currentPage=11)
		//' 11 / 10 = 1' 이며... 여기에 ' * 10 ' (10 을 곱하면) 10 이다.
		//그러면... currentPageSetup 은 10 이 되는 것이다.
		
		//30페이지에 머물고 있어도 커렌트 페이지셋업은 20으로 나와야 한다.
		//그러나 31페이지로 가면 커렌트 페이지셋업은 30으로 나오도록...
		
		//10의 배수일 때 문제가 발생
		if (currentPage % numPerBlock == 0)
		{
			currentPageSetup = currentPageSetup - numPerBlock;
			//currentPageSetup -= numPerBlock;
		}
		//만약 위 처리에서... (라인 90)
		//현재 페이지가 20페이지였다면 (currentPage = 20)
		//' 20 / 10 = 2 ' 이며... 여기에 ' = 10 ' (10을 곱해서) 20이 되는데
		//이와 같은 상황이라면... 다시 10을 빼서 10 으로 만들어주기 위한 구문.
		
		
		
		//1페이지로 가게끔(맨 처음으로)
		//전체 토탈페이지가 
		if((totalPage>numPerBlock) && (currentPageSetup>0))
		{
			strList.append("<a href='" + listUrl + "pageNum=1'>1</a>");
		}
		//-- listUrl 은 위에서 (라인 77) 이미 전처리가 끝난 상황이기 때문에 
		//'...?' 상태 또는 '...?...&'인 상태이다.
		//이로 인해 결과는
		//'...?pageNum=1' 이거나 '...&pageNum=1' 가 되는 상황이다.
		
		
		
		
		//&nbsp;
		//a 태그 앞에다가 공백 일부러 넣음
		
		
		
		
		
		//Prev(이전으로)
		n = currentPage - numPerBlock;
		// n -> 해당 페이지만큼 앞으로 가기 위한 변수
		
		if ( (totalPage>numPerBlock) && (currentPageSetup>0) )
		{
			strList.append(" <a href='" + listUrl + "pageNum=" + n + "'>Prev</a>");
		}
		//-- currentPageSetup 이 0 보다 큰 경우는
		//   이미 페이지가 11 이상이라는 의미이며
		//   이 때, 현재 페이지(currentPage)가 11 이상일 경우
		//   'Prev' 를 붙이기 위한 구문,
		//-- 'Prev'를 클릭할 경우
		//    n 변수 페이지로 이동하는데
		//    12 에서 'Prev' 할 경우 2 페이지가 되고,
		//    22 에서 'Prev' 할 경우 12 페이지가 될 수 있도록 처리하는 방식이다.
		
		
		
		
		
		//각 페이지 바로가기(각 페이지 바로가기)
		page = currentPageSetup + 1;
		//-- '+1' 을 수행하는 이유는
		//   앞에서 currentPageSetup 에서 10을 가져왔다면
		//   10부터 시작하는 것이 아니라
		//   바로가기 페이지는 11부터 시작해야 하기 때문이다.
		
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
		//현재 내 페이지에는 링크가 걸려있으면 안된다.
		//그래서 span 으로 처리
		//반복문 동안 페이지가 하나씩 증가하는데
		//나머지 페이지는 
		//사전에 전처리한 걸로 url 처리
		
		
		
		
		//Next(다음으로)
		n = currentPage + numPerBlock;
		if ((totalPage-currentPageSetup) > numPerBlock)
		{
			strList.append(" <a href='" + listUrl + "pageNum=" + n + "'>Next</a>");
		}
		
		
		
		//마지막 페이지(맨 마지막으로)
		if( (totalPage>numPerBlock) && (currentPageSetup+numPerBlock)<totalPage)
		{
			strList.append(" <a href='" + listUrl + "pageNum=" + totalPage + "'>" + totalPage + "</a>");
		}
		
		
		
		return strList.toString();
	}
	
	
	
	
	
	
	
	
}
