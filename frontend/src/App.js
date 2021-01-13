import './App.css';
import React, {useEffect, useState} from 'react';
import axios from 'axios';

function App() {
  const [data, setData] = useState([]);
  const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT || window.location.href;

  useEffect(() => {
    const fetchData = async () => {
      const result = await axios(
          API_ENDPOINT + 'cakes',
      );

      setData(result.data);
    };

    fetchData();
  }, []);

  return (
      <ul>
        {data.map(item => (
            <li key={item.objectID}>
              <h1>{item.title}</h1>
              <h2>{item.description}</h2>
              <img src={item.image}/>
            </li>
        ))}
      </ul>
  );
}

export default App;
