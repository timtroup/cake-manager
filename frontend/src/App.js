import './App.css';
import React, {useEffect, useState} from 'react';
import {useForm} from "react-hook-form";
import axios from 'axios';

function App() {
    const [data, setData] = useState([]);
    const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT || window.location.href;

    const fetchData = async () => {
        const result = await axios(
            API_ENDPOINT + 'cakes',
        );

        setData(result.data);
    };

    const {register, handleSubmit, errors} = useForm();

    const onSubmit = async (data) => {
        axios.post(API_ENDPOINT + 'cakes', data)
            .then(function (response) {
                console.log(response);
                fetchData();
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <React.Fragment>
            <form onSubmit={handleSubmit(onSubmit)}>
                <input name="title" placeholder="Title" ref={register({required: true})}/>
                <input name="description" placeholder="Description" ref={register({required: true})}/>
                <input name="image" placeholder="Image URL" ref={register({required: true})}/>
                {errors.title && <span>This field is required</span>}
                {errors.description && <span>This field is required</span>}
                {errors.image && <span>This field is required</span>}
                <input type="submit"/>
            </form>
            <ul>
                {data.map(item => (
                    <li key={item.objectID}>
                        <h1>{item.title}</h1>
                        <h2>{item.description}</h2>
                        <img src={item.image}/>
                    </li>
                ))}
            </ul>
        </React.Fragment>
    );
}

export default App;
