import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

export default function TicketConcertpage() { // 콘서트 페이지(대중음악 코드 : CCCD)
const [performanceInfos, setPerformanceInfos] = useState([]); // 공연 데이터
  const [rankPerformanceInfos, setRankPerformanceInfos] = useState([]); // 공연 랭킹 데이터
  const [recommendPerformanceInfos, setRecommendPerformanceInfos] = useState([]); // 추천 공연 데이터
  const [regionPerformanceInfos, setRegionPerformanceInfos] = useState([]); // 지역별 공연 데이터
  const [regionCode, setRegionCode] = useState("11"); // 서울 기본값
  
  const [loading, setLoading] = useState(true);  // 데이터 불러오는 중인지 여부
  const [regionLoading, setRegionLoading] = useState(true); // 지역 데이터 로딩 여부
  
  // 날짜를 YYYYMMDD 형식으로 변환
  function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    return `${year}${month}${day}`;
  }

  //spring에서 데이터 가지고 오기
  useEffect(() => {
    const fetchData = async () => {
      try {
        //공연 기간 정하기(현재날짜 기준 2주전 ~ 2주후) ***임시***
        const today = new Date();

        const twoWeeksBefore = new Date();
        twoWeeksBefore.setDate(today.getDate() - 14);

        const twoWeeksAfter = new Date();
        twoWeeksAfter.setDate(today.getDate() + 14);

        const startDate = formatDate(twoWeeksBefore);
        const endDate = formatDate(twoWeeksAfter);

        console.log("요청 날짜:", startDate, endDate);
        // 전체 공연
        const allResponse = await axios.get('http://localhost:8080/tl/getPerformanceInfo', {
          params: { startdate: startDate, enddate: endDate, cpage: 1, rows: 5, shcate: "CCCD"}
        });
        // 공연 랭킹
        const rankResponse = await axios.get('http://localhost:8080/tl/getPerformanceInfo', {
          params: { startdate: startDate, enddate: endDate, requestType: "rank", shcate: "CCCD" }
        });
        // 추천 공연
        const recommendResponse = await axios.get('http://localhost:8080/tl/getPerformanceInfo', {
          params: { startdate: startDate, enddate: endDate, requestType: "recommend", shcate: "CCCD" }
        });
        console.log(allResponse.data);
        setPerformanceInfos(allResponse.data); // 콘서트 전체 공연 데이터 저장
        console.log(rankResponse.data);
        setRankPerformanceInfos(rankResponse.data); // 콘서트 공연 랭킹 데이터 저장
        console.log(recommendResponse.data);
        setRecommendPerformanceInfos(recommendResponse.data); // 콘서트 추천 공연 데이터 저장
        
        setLoading(false); // loading 완료
      } catch (err) {
        console.error("api 불러오기 실패:", err);
        setLoading(false); // loading 완료
      }
    };
    fetchData();
  }, []);
   // 지역별 콘서트 공연만 별도 useEffect
  useEffect(() => {
    const fetchRegionData = async () => {
      try {
        setRegionLoading(true);

        const today = new Date();
        const twoWeeksBefore = new Date();
        twoWeeksBefore.setDate(today.getDate() - 14);
        const twoWeeksAfter = new Date();
        twoWeeksAfter.setDate(today.getDate() + 14);

        const startDate = formatDate(twoWeeksBefore);
        const endDate = formatDate(twoWeeksAfter);

        const regionResponse = await axios.get("http://localhost:8080/tl/getPerformanceInfo", {
          params: { startdate: startDate, enddate: endDate, cpage: 1, rows: 5, signgucode: regionCode }
        });
        console.log(regionResponse.data);
        setRegionPerformanceInfos(regionResponse.data); // 지역별 콘서트 공연 데이터 저장
        setRegionLoading(false);
      } catch (err) {
        console.error("지역 데이터 불러오기 실패:", err);
        setRegionLoading(false);
      }
    };

    fetchRegionData();
  }, [regionCode]); // regionCode 바뀔 때마다 실행
  if (loading) return <div>불러오는 중...</div>; // 로딩중일때 출력

  return (
    <div>
      <h2>전체 공연</h2>
      <div style={{ display: "flex" }}>
        {performanceInfos.map((performanceInfo, idx) => ( //전체 콘서트 공연 정보 출력
          <div key={idx} style={{width:"120px", height:"160px", ml:"15px", mr:"15px"}}>
            <Link to="/ticket/info" state={{ performanceInfo }} style={{ display: "flex", flexDirection:"column", alignItems:"center" }}>
              <img alt={performanceInfo.per_title} style={{ width: "100px", height: "120px" }} src={performanceInfo.per_poster} /> {/* 포스터 이미지 */}
              <h6>{performanceInfo.per_title}</h6> {/* 콘서트 공연 제목 */}
            </Link>
          </div>
        ))}
      </div>
      <h2>공연 랭킹</h2>
      <div style={{ display: "flex" }}>
      {rankPerformanceInfos.map((performanceInfo, idx) => ( // 콘서트 공연 랭킹 정보 출력
          <div key={idx} style={{width:"120px", height:"160px", ml:"15px", mr:"15px"}}>
            <Link to="/ticket/info" state={{ performanceInfo }} style={{ display: "flex", flexDirection:"column", alignItems:"center" }}>
              <img alt={performanceInfo.per_title} style={{ width: "100px", height: "120px" }} src={performanceInfo.per_poster} /> {/* 포스터 이미지 */}
              <h6>{performanceInfo.per_rank}등 : {performanceInfo.per_title}</h6> {/* 콘서트 공연 등수, 제목 */}
            </Link>
          </div>
        ))}
      </div>
      <h2>추천 공연</h2>
      <div style={{ display: "flex" }}>
      {recommendPerformanceInfos.map((performanceInfo, idx) => ( // 추천 공연 정보 출력
          <div key={idx} style={{width:"120px", height:"160px", ml:"15px", mr:"15px"}}>
            <Link to="/ticket/info" state={{ performanceInfo }} style={{ display: "flex", flexDirection:"column", alignItems:"center" }}>
              <img alt={performanceInfo.per_title} style={{ width: "100px", height: "120px" }} src={performanceInfo.per_poster} /> {/* 포스터 이미지 */}
              <h6>{performanceInfo.per_title}</h6> {/* 공연 제목 */}
            </Link>
          </div>
        ))}
      </div>
      <h2>지역별 공연</h2>
      {/* 지역 선택 버튼 */}
      <div style={{ marginBottom: "10px" }}>
        <button onClick={() => setRegionCode("11")}>서울</button>
        <button onClick={() => setRegionCode("28")}>인천</button>
        <button onClick={() => setRegionCode("26")}>부산</button>
        <button onClick={() => setRegionCode("47")}>경상</button>
        <button onClick={() => setRegionCode("41")}>경기</button>
        <button onClick={() => setRegionCode("43")}>충청</button>
        <button onClick={() => setRegionCode("45")}>전라</button>
        <button onClick={() => setRegionCode("51")}>강원</button>
        <button onClick={() => setRegionCode("50")}>제주</button>
      </div>

      {regionLoading ? (
        <div>지역 공연 불러오는 중...</div>
      ) : (
        <div style={{ display: "flex" }}>
          {regionPerformanceInfos.map((performanceInfo, idx) => (
            <div key={idx} style={{ width: "120px", height: "160px", margin: "0 15px" }}>
              <Link to="/ticket/info" state={{ performanceInfo }} style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                <img alt={performanceInfo.per_title} style={{ width: "100px", height: "120px" }} src={performanceInfo.per_poster} />
                <h6>{performanceInfo.per_title}</h6>
              </Link>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}