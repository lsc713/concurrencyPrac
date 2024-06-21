/*eslint-disable*/

import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import axios from "axios";

function App() {
  const [hello, setHello] = useState('');
  const [title, setTitle] = useState(['1', '2', '3']);
  const [content, setContent] = useState(['1', '2', '3']);
  const [likes, setLikes] = useState([0, 0, 0]);
  const [modals, setModals] = useState([false, false, false]);
  let [input, changeInput] = useState("");

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

  const handleAddPost = () => {
    const newTitles = [...title];
    const newContents = [...content];
    const newLikes = [...likes];
    const newModals = [...modals];
    newTitles.unshift(input);
    newContents.unshift(input);
    newLikes.unshift(0);
    newModals.unshift(false);
    setTitle(newTitles);
    setContent(newContents);
    setLikes(newLikes);
    setModals(newModals);
    changeInput(""); // 입력 필드 초기화
  };


  const handleDeletePost = (index) => {
    const newTitles = title.filter((_, i) => i !== index);
    const newContents = content.filter((_, i) => i !== index);
    const newLikes = likes.filter((_, i) => i !== index);
    const newModals = modals.filter((_, i) => i !== index);
    setTitle(newTitles);
    setContent(newContents);
    setLikes(newLikes);
    setModals(newModals);
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
                  {title[index]}
                  <span onClick={(event) => {
                    event.stopPropagation();
                    handleLikeClick(event, index)
                  }}>👍</span>{likes[index]}
                  <button onClick={() => handleDeletePost(index)}>🪣</button>

                </h4>
                <p>content</p>
                {modals[index] && <Modal title={title[index]}
                                         content={content[index]}
                                         onChange={() => handleTitleChange(
                                             index, "new title")}/>}
              </div>
          ))
        }
        <input value={input} onChange={(e) => changeInput(e.target.value)}/>
        <button onClick={handleAddPost}>발행</button>
      </div>
  );
}

function Modal({title, content, onChange}) {
  return (
      <div className="modal">
        <h4>{title}</h4>
        <p>날짜</p>
        <p>{content}</p>
        <button onClick={onChange}>글 수정</button>
      </div>
  )
}

export default App;