import {TokenData} from "./OauthAPI";
import axios from "axios";
import {Base64} from "js-base64";

export enum StatusType{
    ACTIVE,SUSPENDED,BANNED
}

export interface User {
    email: string,
    name: string,
    surname: string,
    admin: boolean,
    fiscalCode:string,
    residence:string,
    birthDate:number,
    domicile:string,
    status:StatusType,
    telephonNumber: number,
    authorizeBonus: boolean,
    authorizeVaxine: boolean,
}

export const getLoggedUser = (tokenData: TokenData) => {
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/userAPI/v1/user/get/' + tokenData.email+ '?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return axios.get<User>(url).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const getAllUsers = (tokenData: TokenData) => {
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/userAPI/v1/users/?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return axios.get<User[]>(url).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const updateUser=async (tokenData: TokenData, newUser: User): Promise<boolean> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/userAPI/v1/user/update/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.post(url,
        JSON.stringify(newUser),
        {
            headers: {"Content-Type": "text/plain"}
        })
        .then(value => {
            return value.data;
        }).catch(function (error) {
            console.log(error)
            return false;
        });
}


