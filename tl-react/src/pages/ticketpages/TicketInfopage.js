import { Link, useLocation } from "react-router-dom";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";

// 마커 아이콘 설정 (기본 아이콘이 깨질 수 있음)
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl:
        "https://unpkg.com/leaflet@1.9.3/dist/images/marker-icon-2x.png",
    iconUrl: "https://unpkg.com/leaflet@1.9.3/dist/images/marker-icon.png",
    shadowUrl: "https://unpkg.com/leaflet@1.9.3/dist/images/marker-shadow.png",
});

export default function TicketInfopage() {
    const location = useLocation();
    const { performanceInfo } = location.state || {}; // 공연 상세 정보
    console.log(performanceInfo.per_latitude);
    const position = { lat: Number(performanceInfo.per_latitude), lng: Number(performanceInfo.per_longitude) } // 위도, 경도
    return (
        <div> {/*공연 상세 정보 출력 */}
            <h4>공연 포스터</h4>
            <img alt={performanceInfo.per_title} src={performanceInfo.per_poster} style={{ width: "200px", height: "240px" }} />
            <h4>공연 제목 : {performanceInfo.per_title}</h4>
            <h4>공연 기간 : {performanceInfo.per_startD} ~ {performanceInfo.per_endD}</h4> {/* 공연 시작 ~ 종료 일자 */}
            <h4>공연 장소 : {performanceInfo.per_peace}</h4>
            <h4>공연 관람시간 : {performanceInfo.per_runT}</h4>
            <h4>공연 일정 : {performanceInfo.per_sche}</h4>
            <h4>공연 장르 : {performanceInfo.per_genre}</h4>
            <h4>티켓 예약</h4>
            {performanceInfo.per_ticket.map((ticket, idx) => (
                <div key={idx}>
                    <input type="button" value={ticket.name} onClick={() => window.open(ticket.url, "_blank")} /> {/* 티켓 예약처 이름, 링크 */}
                </div>
            ))}
            <h4>지도</h4>
            <MapContainer center={position} zoom={16} style={{ width: "500px", height: "400px" }}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <Marker position={position}> {/* 지도 마커 */}
                    <Popup>
                        <div styl>
                            <h5>{performanceInfo.per_peace}</h5>
                            <h6>{performanceInfo.per_address}</h6>
                        </div>
                    </Popup> {/* 마커 팝업 */}
                </Marker>
            </MapContainer>
        </div>
    )
}