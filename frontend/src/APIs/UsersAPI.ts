import {TokenData} from "./oauthAPI";
import axios from "axios";
import {Base64} from "js-base64";

export enum StatusType{
    ACTIVE,SUSPENDED,BANNED
}

export type UserType = {
    email: string,
    name: string,
    surname: string,
    admin: boolean,
    fiscalCode:string,
    residence:string,
    birthDate:number,
    domicile:string,
    status:StatusType,
    telephonNumber: number
}

export const getLoggedUser = async (tokenData: TokenData) => {
    console.log('recupero utente', tokenData)
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/userAPI/v1/user/get/' + tokenData.email+ '?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const getAllUsers = async (tokenData: TokenData) => {
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/userAPI/v1/users/?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}


const configGeneratorEncoder=(base_url: string, tokenData:TokenData, value:string)=>{
    value = Base64.encode(value)
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url= base_url+tokenData.email+'?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    console.log('url',url)
    return  {
        method:'post',
        url:url,
        data: value
    }
}

export const updateDomicile=async (domicile:string,tokenData: TokenData)=>{
    console.log('domicile',domicile)
    const config=configGeneratorEncoder('http://localhost:8080/userAPI/v1/user/update/domicile/',tokenData,domicile)
    return axios(config).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const updateResidence=async (residence:string,tokenData: TokenData)=>{
    console.log('residence',residence)
    const config= configGeneratorEncoder('http://localhost:8080/userAPI/v1/user/update/residence/',tokenData,residence)
    return axios(config).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const updateTelephoneNumber=async (telephoneNumber:string,tokenData: TokenData)=>{
    console.log('tephone',telephoneNumber)
    const config= configGeneratorEncoder('http://localhost:8080/userAPI/v1/user/update/telephoneNumber/',tokenData,telephoneNumber)
    console.log('config',config)
    return axios(config).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const updateName=async (name:string,tokenData: TokenData)=>{
    console.log('name',name)
    const config= configGeneratorEncoder('http://localhost:8080/userAPI/v1/user/update/name/',tokenData,name)
    return axios(config).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const updateSurname=async (surname:string,tokenData: TokenData)=>{
    console.log('surname',surname)
    const config= configGeneratorEncoder('http://localhost:8080/userAPI/v1/user/update/surname/',tokenData,surname)
    return axios(config).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const updateBirthDate=async (birthDate:string,tokenData: TokenData)=>{
    console.log('birthDate',birthDate)
    const config= configGeneratorEncoder('http://localhost:8080/userAPI/v1/user/update/birthDayDate/',tokenData,birthDate)
    return axios(config).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const updateFiscalCode=async (fiscalCode:string,tokenData: TokenData)=>{
    console.log('fiscalCode',fiscalCode)
    const config= configGeneratorEncoder('http://localhost:8080/userAPI/v1/user/update/fiscalCode/',tokenData,fiscalCode)
    return axios(config).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const updateUser=async (tokenData: TokenData, newUser: UserType)=>{
    console.log('newUser',newUser)
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url= 'http://localhost:8080/userAPI/v1/user/update/' + tokenData.email+ '?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    console.log('url',url)
    const config= {
        method:'post',
        url:url,
        data: newUser
    }
    axios(config).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}


