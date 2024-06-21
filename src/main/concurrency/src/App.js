/*eslint-disable*/

import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import axios from "axios";

function App() {
  const [hello, setHello] = useState('');
  const [title, setTitle] = useState(['1', '2', '3']);
  const [likes, setLikes] = useState([0, 0, 0]);
  const [modals, setModals] = useState([false, false, false]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/v1/auth/signup')
    .then((res) => {
      setHello(res.data);
    })
    .catch((err) => {
      console.error("Error fetching data: ", err);
    });
  }, []);

  const handleTitleClick = (index) => {
    const newModals = [...modals];
    newModals[index] = !newModals[index];
    setModals(newModals);
    const newTitles = [...title];
    newTitles[index] = '추천게시물';
    setTitle(newTitles);
  };

  const handleLikeClick = (event, index) => {
    event.stopPropagation();
    const newLikes = [...likes];
    newLikes[index] += 1;
    setLikes(newLikes);
  };

  const handleTitleChange = (index, newTitle) => {
    const newTitles = [...title];
    newTitles[index] = newTitle;
    setTitle(newTitles);
  };

  return (
      <div className="App">
        <div className="black-nav">
          <h4>상단바</h4>
        </div>
        {
          title.map((e, index) => (
              <div key={index} className="list">
                <h4 onClick={() => handleTitleClick(index)}>
                  {title[index]} <span onClick={(event) => handleLikeClick(event, index)}>👍</span>{likes[index]}
                </h4>
                <p>content</p>
                {modals[index] && <Modal title={title[index]} onChange={()=> handleTitleChange(index, "new title")} />}
              </div>
          ))
        }
        백엔드 데이터 : {hello}
      </div>
  );
}

function Modal({ title,onChange}) {
  return (
      <div className="modal">
        <h4>{title}</h4>
        <p>날짜</p>
        <p>상세내용</p>
        <button onClick={onChange}>글 수정</button>
      </div>
  )
}

export default App;