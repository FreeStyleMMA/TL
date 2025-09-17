import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from "react-router-dom";

export default function ReplyList({ replys }) {
  const { no } = useParams();
  const [replyList, setReplyList] = useState([]);

  useEffect(() => {
    const fetchReply = async (e) => {
      try {
        const response = await axios.get(`http://localhost:8080/reply/read?originNo=${no}`);
        setReplyList(response.data);
        console.log(ReplyList);

      } catch (error) {
        console.log(error);
      }
    }
    fetchReply();

  }, [no]);


  return (
    <div className="replyLayout">
      {replyList.map(reply => (
        <div key={reply.no}>
          {reply.memberId}<br />
          {reply.date}<br />
          {reply.content}<br />
        </div>
      ))}

    </div>
  )
}