import axios from 'axios';
import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom'
import './PostWrite.css';
import { useAuth } from "../auth/AuthContext";

export default function PostWrite() {
  const [category, setCategory] = useState("reviewBoard");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [memberId, setMemberId] = useState("");
  const [media, setMedia] = useState("");
  const [preview, setPreview] = useState(null);

  const { member } = useAuth();
  const navigate = useNavigate();

  const textareaRef = useRef(null); // textarea ref

  useEffect(() => {
    if (member) setMemberId(member.memberId);
  }, [member]);

  // 자동 높이 조절
  const handleContentChange = (e) => {
    setContent(e.target.value);
    const ta = textareaRef.current;
    if (ta) {
      ta.style.height = "auto"; // 초기화
      ta.style.height = ta.scrollHeight + "px"; // 내용에 맞춰 높이 조절
    }
  };

  const handleMediaChange = (e) => {
    const file = e.target.files[0];
    setMedia(file);
    setPreview(URL.createObjectURL(file));
  };

  const handlePost = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("post", new Blob([JSON.stringify({
      category,
      title,
      content,
      memberId,
    })], { type: "application/json" }));
    if (media) formData.append("media", media);

    try {
      const response = await axios.post('http://localhost:8080/post/write', formData);
      console.log(response.data);
      navigate(-1); // 이전 페이지로 이동
    } catch (error) {
      console.error("업로드 실패", error);
    }
  };

  return (
    <div>
      <form className='postLayout' onSubmit={handlePost}>
        <div id="pw_category">
          <label htmlFor='category' id="pw_catecory_text">카테고리</label>
          <select className='select' name='category' onChange={(e) => setCategory(e.target.value)}>
            <option value='reviewBoard'>리뷰</option>
            <option value='freeBoard'>잡담</option>
          </select>
        </div>
        <div id="pw_writer">
          작성자 id : &nbsp;&nbsp;  {memberId}
        </div>
        <input
          className='titleBox'
          value={title}
          placeholder='제목을 입력하세요'
          onChange={(e) => setTitle(e.target.value)}
        />
        <textarea
          ref={textareaRef}
          className='textBox'
          value={content}
          placeholder='내용을 입력하세요'
          onChange={handleContentChange}
        />
        <input type="file" className='file' accept="image/*,video/*" onChange={handleMediaChange} />
        {preview && <img src={preview} alt="preview" width="100" />}
        <button className='submitButton' type="submit">글쓰기</button>
      </form>
    </div>
  );
}
