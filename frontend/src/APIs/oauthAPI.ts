import axios from 'axios';
import {Base64} from "js-base64";

export type TokenData= {
    email:string,
    provider:string,
    token:string,
}

export const exchangeToken = async (token: string)=> {
    console.log('qui ho questo',token)
    let url = 'http://localhost:8080/authAPI/v1/token/'+ token
    return await axios.get(url).then((response) => {
        const data = Base64.decode(response.data);
        const values = data.split(",")
        console.log('values trovati',values)
        for(let i = 0; i < values.length; i++){
            values[i] = values[i].split(": ")[1]
        }
        console.log('values splittati',values)
        return{
            email: values[0],
            provider: values[1],
            token: values[2],
        }
    })
}

