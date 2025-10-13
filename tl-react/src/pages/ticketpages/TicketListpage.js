import { useLocation, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

export default function TicketResearchpage() {
  const location = useLocation();

  const genre = location.state?.genre || ""; // 장르
  const page = location.state?.page || 1; // 검색한 페이지
  const type = location.state?.type || ""; // 요청 타입
  const region = location.state?.region || ""; // 지역 코드
  const [performanceInfos, setPerformanceInfos] = useState([]); // 공연 데이터
  const [loading, setLoading] = useState(true);  // 데이터 불러오는 중인지 여부
  const [hasNextPage, setHasNextPage] = useState(false); // 다음 페이지 존재 여부
  
  // 날짜를 YYYYMMDD 형식으로 변환
  function formatDate(date) {
    const d = typeof date === "string" ? new Date(date) : date;
    
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, "0");
    const day = String(d.getDate()).padStart(2, "0");
    return `${year}${month}${day}`;
  }

  // spring에서 데이터 가지고 오기
  useEffect(() => {
    setLoading(true);
    const fetchData = async () => {
      try {
        const today = new Date();
        const twoWeeksBefore = new Date();
        twoWeeksBefore.setDate(today.getDate() - 14);
        const twoWeeksAfter = new Date();
        twoWeeksAfter.setDate(today.getDate() + 14);

        const startDate = formatDate(twoWeeksBefore);
        const endDate = formatDate(twoWeeksAfter);

        // 현재 페이지 데이터
        const response = await axios.get('http://localhost:8080/tl/getPerformanceInfo', {
          params: { startdate: startDate, enddate: endDate, cpage: page, rows: 10, signgucode: region, perRequestT : type, shcate : genre }
        });
        setPerformanceInfos(response.data);

        // 다음 페이지 존재 여부 확인
        const nextResponse = await axios.get('http://localhost:8080/tl/getPerformanceInfo', {
          params: { startdate: startDate, enddate: endDate, cpage: page + 1, rows: 10, signgucode: region, perRequestT : type, shcate : genre }
        });
        setHasNextPage(nextResponse.data.length > 0);

        setLoading(false);
      } catch (err) {
        console.error("API 불러오기 실패:", err);
        setLoading(false);
      }
    };
    fetchData();
  }, [page]);

  if (loading) return <div>불러오는 중...</div>; // 로딩중일때 출력

  return (
    <div>
      <h2>전체 결과</h2>

        <div style={{ display: "flex", flexWrap: "wrap" }}>
          {performanceInfos.map((performanceInfo, idx) => (
            <div key={idx} style={{ width: "120px", height: "160px", margin: "0 15px 15px 0" }}>
              <Link
                to="/ticket/info"
                state={{ performanceInfo }}
                style={{ display: "flex", flexDirection: "column", alignItems: "center" }}
              >
                <img
                  alt={performanceInfo.perTitle}
                  style={{ width: "100px", height: "120px" }}
                  src={performanceInfo.perPoster}
                />
                <h6>{performanceInfo.perTitle}</h6>
              </Link>
            </div>
          ))}
        </div>

    {/* 이전/다음 페이지 버튼 */}
    <div style={{ display: "flex", justifyContent: "center", alignItems:"center", marginTop: "20px" }}>
    {page > 1 && (
        <Link
        to="/ticket/list"
        state={{ page : page - 1, region: region, type : type, genre : genre }}
        style={{
            display: "inline-block",
            marginRight : "10px",
            height:"auto",
            background: "#ddd",
            textDecoration: "none",
            borderRadius: "4px",
            color: "#000",
        }}
        >
        이전
        </Link>
    )}
    <h4>{page}</h4>
    {hasNextPage && (
        <Link
        to="/ticket/list"
        state={{ page : page + 1, region: region, type : type, genre : genre }}
        style={{
            display: "inline-block",
            marginLeft : "10px",
            height:"auto",
            background: "#ddd",
            textDecoration: "none",
            borderRadius: "4px",
            color: "#000",
        }}
        >
        다음
        </Link>
    )}
    </div>
    </div>
  );
}
