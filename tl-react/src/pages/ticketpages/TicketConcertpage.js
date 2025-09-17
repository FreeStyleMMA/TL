import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

export default function TicketConcertpage() { // 콘서트 페이지 (장르 코드 : CCCD)
  const [performanceInfos, setPerformanceInfos] = useState([]);
  const [rankPerformanceInfos, setRankPerformanceInfos] = useState([]);
  const [recommendPerformanceInfos, setRecommendPerformanceInfos] = useState([]);
  const [regionPerformanceInfos, setRegionPerformanceInfos] = useState([]);
  const [regionCode, setRegionCode] = useState("11");
  const [loading, setLoading] = useState(true);
  const [statusLoading, setStatusLoading] = useState(true);
  const [regionLoading, setRegionLoading] = useState(true);
  //지역 코드
  const regions = [
    { code: "11", name: "서울" },
    { code: "28", name: "인천" },
    { code: "26", name: "부산" },
    { code: "41", name: "경기" },
    { code: "51", name: "강원" },
    { code: "50", name: "제주" },
    { code: "43", name: "충북" },
    { code: "44", name: "충남" },
    { code: "45", name: "전북" },
    { code: "46", name: "전남" },
    { code: "47", name: "경북" },
    { code: "48", name: "경남" },
  ];

  //기본 날짜 설정 (오늘 기준 2주전 ~ 2주후) ***임시***
  function getDefaultDates() {
    const today = new Date();
    const twoWeeksBefore = new Date();
    twoWeeksBefore.setDate(today.getDate() - 14);
    const twoWeeksAfter = new Date();
    twoWeeksAfter.setDate(today.getDate() + 14);

    return {
      startDate: twoWeeksBefore.toISOString().split("T")[0],
      endDate: twoWeeksAfter.toISOString().split("T")[0],
    };
  }

  // 기본 날짜
  const { startDate, endDate } = getDefaultDates();

  // 날짜 데이터를 YYYYMMDD형식의 문자열로 반환하는 함수
  function formatDate(date) {
    const year = date.getFullYear(); // date에서 년도 추출
    const month = String(date.getMonth() + 1).padStart(2, "0"); // date에서 월 추출(1자리일 경우에 앞에 0 추가)
    const day = String(date.getDate()).padStart(2, "0"); // date에서 일 추출(1자리일 경우에 앞에 0 추가)
    return `${year}${month}${day}`;
  }

  // 추천 공연, 공연 랭킹
  useEffect(() => {
    const fetchStatusData = async (sDate, eDate) => {
      try {
        setStatusLoading(true);

        const rankResponse = await axios.get("http://localhost:8080/tl/getPerformanceInfo", {
          params: { startdate: sDate, enddate: eDate, rows: 5, cpage: 1, shcate: "CCCD", requestType: "rank" },
        });
        const recommendResponse = await axios.get("http://localhost:8080/tl/getPerformanceInfo", {
          params: { startdate: sDate, enddate: eDate, rows: 5, cpage: 1, shcate: "CCCD", requestType: "recommend" },
        });

        setRankPerformanceInfos(rankResponse.data);
        setRecommendPerformanceInfos(recommendResponse.data);
        setStatusLoading(false);
      } catch (err) {
        console.error("api 불러오기 실패:", err);
        setStatusLoading(false);
      }
    }
    if (startDate && endDate) fetchStatusData(formatDate(new Date(startDate)), formatDate(new Date(endDate)));
  }, [startDate, endDate]);

  // 전체 공연
  useEffect(() => {
    const fetchData = async (sDate, eDate) => {
      try {
        setLoading(true);
        const allResponse = await axios.get("http://localhost:8080/tl/getPerformanceInfo", {
          params: { startdate: sDate, enddate: eDate, cpage: 1, rows: 5, shcate: "CCCD" },
        });

        setPerformanceInfos(allResponse.data);
        setLoading(false);
      } catch (err) {
        console.error("api 불러오기 실패:", err);
        setLoading(false);
      }
    };
    if (startDate && endDate) fetchData(formatDate(new Date(startDate)), formatDate(new Date(endDate)));
  }, [startDate, endDate]);

  // 지역별 공연
  useEffect(() => {
    const fetchRegionData = async (sDate, eDate) => {
      try {
        setRegionLoading(true);
        const regionResponse = await axios.get("http://localhost:8080/tl/getPerformanceInfo", {
          params: { startdate: sDate, enddate: eDate, cpage: 1, rows: 5, signgucode: regionCode, shcate: "CCCD" },
        });
        setRegionPerformanceInfos(regionResponse.data);
        setRegionLoading(false);
      } catch (err) {
        console.error("지역 데이터 불러오기 실패:", err);
        setRegionLoading(false);
      }
    };

    if (startDate && endDate) fetchRegionData(formatDate(new Date(startDate)), formatDate(new Date(endDate)));
  }, [regionCode, startDate, endDate]);

  // 공연 목록 출력
  function PerformanceList({ data, loading }) {
    if (loading) return <div>불러오는 중...</div>

    return (
      <div style={{ display: "flex", flexWrap: "wrap" }}>
        {data?.map((performanceInfo, idx) => (
          <div key={idx} style={{ width: "120px", height: "160px", margin: "0 10px" }}>
            <Link to="/ticket/info" state={{ performanceInfo }} // 공연 상세 정보 페이지로 이동
              style={{ display: "flex", flexDirection: "column", alignItems: "center" }}
            >
              <img alt={performanceInfo.per_title} src={performanceInfo.per_poster} //공연 포스터
                style={{ width: "100px", height: "120px" }}
              />
              <h6>{performanceInfo.per_title}</h6> {/* 공연 제목 */}
            </Link>
          </div>
        ))}
      </div>
    );
  }

  return (
    <div>
      {/* 공연 랭킹 */}
      <div style={{ display: "flex", alignItems: "center", margin: "20px" }}>
        <h2>공연 랭킹</h2>
        <Link to="/ticket/list" state={{ type: "rank", genre : "CCCD" }} // 모두 보기 버튼
          style={{ margin: "5px", border: "1px, solid, black", }}
        >
          모두 보기
        </Link>
      </div>
      <PerformanceList data={rankPerformanceInfos} loading={statusLoading} />
      {/* 추천 공연 */}
      <h2>추천 공연</h2>
      <PerformanceList data={recommendPerformanceInfos} loading={statusLoading} />

      {/* 전체 공연 */}
      <div style={{ display: "flex", alignItems: "center", margin: "20px" }}>
        <h2>전체 공연</h2>
        <Link to="/ticket/list" state={{ genre : "CCCD" }} // 모두 보기 버튼
          style={{ margin: "5px", border: "1px, solid, black", }}
        >
          모두 보기
        </Link>
      </div>
      <PerformanceList data={performanceInfos} loading={loading} />

      {/* 지역별 공연 */}
      <div style={{ display: "flex", alignItems: "center", margin: "20px" }}>
        <h2>지역별 공연</h2>
        <Link to="/ticket/list" state={{ region: regionCode, genre : "CCCD" }} // 모두 보기 버튼
          style={{ margin: "5px", border: "1px, solid, black", }}
        >
          모두 보기
        </Link>
      </div>
      <div style={{ marginBottom: "10px" }}>
        {regions.map((region) => (
          <button key={region.code} onClick={() => setRegionCode(region.code)}>
            {region.name}
          </button>
        ))}
      </div>

      <PerformanceList data={regionPerformanceInfos} loading={regionLoading} />
    </div>
  );
}
