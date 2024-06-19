/*eslint-disable*/

import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import axios from "axios";

function App() {
  const [hello, setHello] = useState('');
  let [title, settitle] =useState(['1','2','3'])

  useEffect(() => {
    axios.get('http://localhost:8080/api/v1/auth/signup')
    .then((res) => {
      setHello(res.data);
    })
    .catch((err) => {
      console.error("Error fetching data: ", err);
    });
  }, []);

  return (
      <div className="App">
        <div className="black-nav">
          <h4>상단바</h4>
        </div>
        <div className="list">
          <h4>{title[0]}</h4>
          <p>content</p>
        </div>
        <div className="list">
          <h4>{title[0]}</h4>
          <p>content</p>
        </div>
        <div className="list">
          <h4>{title[0]}</h4>
          <p>content</p>
        </div>
        백엔드 데이터 : {hello}
      </div>
  );
}

export default App;