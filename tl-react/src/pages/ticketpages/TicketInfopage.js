import { Link, useLocation } from "react-router-dom";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import { useContext, useEffect } from "react";
import "./TicketInfopage.css"
import "./reset.css";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import { FavoriteContext } from "../../context/FavoriteContext";
import { useAuth } from '../auth/AuthContext';

// 마커 아이콘 설정 (기본 아이콘이 깨질 수 있음)
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl:
        "https://unpkg.com/leaflet@1.9.3/dist/images/marker-icon-2x.png",
    iconUrl: "https://unpkg.com/leaflet@1.9.3/dist/images/marker-icon.png",
    shadowUrl: "https://unpkg.com/leaflet@1.9.3/dist/images/marker-shadow.png",
});

export default function TicketInfopage() {
    const { handleFavorite, liked } = useContext(FavoriteContext);
    const { member } = useAuth();

    const location = useLocation();
    const { performanceInfo } = location.state || {}; // 공연 상세 정보
    console.log(performanceInfo.perLatitude);
    const position = { lat: Number(performanceInfo.perLatitude), lng: Number(performanceInfo.perLongitude) } // 위도, 경도

    useEffect(() => {
        handleFavorite(member?.memberId, performanceInfo?.perId)
    }, [])
    return (
        <div id='my_layout'> {/*공연 상세 정보 출력 */}
            <div id='ti_top'>
                <div id="ti_top_left">
                    <img alt={performanceInfo.perTitle}
                        src={performanceInfo.perPoster}
                        id='ti_top_img' />
                </div>
                <div id='ti_top_right'>
                    <div id='ti_top_right1'>
                        <div id='ti_ganre'> {performanceInfo.perGenre}  &nbsp;</div>
                        <div id='ti_title'> &lt; {performanceInfo.perTitle} &gt;
                        </div>
                    </div>
                    <div id='ti_top_right2'>
                        <div className='ti_top_text'>장소&nbsp;&nbsp;&nbsp;&nbsp;  {performanceInfo.perPlace}</div>
                        <div className='ti_top_text'>기간&nbsp;&nbsp;&nbsp;&nbsp;  {performanceInfo.perStartD} ~ {performanceInfo.perEndD} </div>
                        {/* 공연 시작 ~ 종료 일자 */}
                    </div>
                    <div id='ti_top_right2_5'>
                        <div className='ti_top_text'>관람 시간&nbsp;&nbsp;&nbsp;&nbsp;  {performanceInfo.perRunT}</div>
                        <div className='ti_top_text'>가격 &nbsp;&nbsp; {performanceInfo.perPrice}</div>
                    </div>


                    <div id='ti_top_right3'>
                        <div id='ti_top_right3_l'>
                            <div className='ti_top_text_2'>
                                공연 일정&nbsp;&nbsp;&nbsp;&nbsp;   {performanceInfo.perSche}
                            </div>

                        </div>
                    </div>
                    <div id='ti_top_right4'>
                        <div id='ti_top_right4_l'>
                            <button
                                onClick={() => handleFavorite(member.memberId, performanceInfo.perId)}
                                style={{ bacgroundcolor: 'white', all: 'unset' }}
                                id="re">
                                <img
                                    src={liked[performanceInfo.perId] === 1 ? "/images/like.png" : "/images/like_grey.png"}
                                    // alt={liked[performanceInfo.perId] ? "좋아요 취소" : "좋아요"}
                                    id="re_img" style={{ width: 25, height: 25 }}
                                />
                            </button>
                        </div>
                        <div id='ti_top_right4_r'>
                            &nbsp;&nbsp;
                            {performanceInfo.perTicket.map((ticket, idx) => (
                                <div key={idx}>
                                    <input
                                        type="button"
                                        className="ti_button"
                                        value={ticket.name}
                                        onClick={() => window.open(ticket.url, "_blank")} /> {/* 티켓 예약처 이름, 링크 */}
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>


            <div id='ti_mid'>
                <div id="tl_mid_title">장소 정보</div>
                <div id="tl_mid_text">장소 : {performanceInfo.perPlace}</div>
                <div id="tl_mid_text">주소 : {performanceInfo.perAddress}</div>
                <MapContainer center={position} zoom={16} style={{ width: "500px", height: "400px", marginTop: "20px" }}>
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    <Marker position={position}> {/* 지도 마커 */}
                        <Popup>
                            <div>
                                <h5>{performanceInfo.perPeace}</h5>
                                <h6>{performanceInfo.perAddress}</h6>
                            </div>
                        </Popup> {/* 마커 팝업 */}
                    </Marker>
                </MapContainer>
            </div>
        </div>
    )
}