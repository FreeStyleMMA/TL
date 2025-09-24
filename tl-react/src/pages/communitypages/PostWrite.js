import axios from 'axios';
import React, { useState, useEffect } from 'react';
import './PostWrite.css';
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from 'react-router-dom';

export default function PostWrite() {
  const [category, setCategory] = useState("reviewBoard");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [memberId, setMemberId] = useState("");
  const [media, setMedia] = useState("");
  const [preview, setPreview] = useState(null);

  const handleMediaChange = (e) => {
    const file = e.target.files[0];
    setMedia(file);
    setPreview(URL.createObjectURL(file));
  };
  const { member } = useAuth();
  console.log("member:", member);

  useEffect(() => {
    if (member) {
      setMemberId(member.memberId);
    }
  }, [member]);


  const handlePost = async (e) => {
    e.preventDefault();
    const formData = new FormData(); //서버에 전송할 데이터 세팅
    formData.append("post", new Blob([JSON.stringify({
      category,
      title,
      content,
      memberId,
      //date,날짜는 DB나 서버에서.
    })], { type: "application/json" }));
    if (media && media.size > 0) {
      formData.append("media", media);
    }
    try {
      const response = await axios.post('http://localhost:8080/post/write', formData)
      console.log(response.data, formData);
    }
    catch (error) {
      console.error("업로드 실패", error);
    }
    console.log(formData.get("post")); // Blob 확인
    console.log(formData.get("media")); // File 확인

    window.history.back();
  }

  return (
    <div>
      <form className='postLayout' onSubmit={handlePost} >
        <label htmlFor='category'>카테고리</label>
        <select className='select' name='category' onChange={(e) => setCategory(e.target.value)}>
          <option value='reviewBoard'>후기게시판</option>
          <option value='freeBoard'>자유게시판</option>
        </select>
        작성자 id:{memberId}
        <input className='titleBox' value={title} placeholder='제목을 입력하세요' onChange={(e) => setTitle(e.target.value)} />
        <textarea className='textBox' value={content} placeholder='내용을 입력하세요' onChange={(e) => setContent(e.target.value)} />
        <input type="file" className='file' accept="image/*,video/*" onChange={handleMediaChange} />
        {preview && <img src={preview} alt="preview" width="100" />}
        <button className='submitButton' type="submit">글쓰기</button>
      </form>
    </div>
  )
}