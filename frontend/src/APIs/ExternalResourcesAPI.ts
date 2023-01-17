import axios from 'axios';
import {TokenData} from "./OauthAPI";
import {Base64} from "js-base64";

export type BonusType={
    id: string,
    name:string,
    description:string,
    email_owner:string,
    type:string,
    end_date:number,
}

export type VaccineType={
     id:string;//ok
     email_owner:string;//ok
     type:string; //ok
     date:number;
     location:string;//ok
     description:string;//ok
     name:string;//ok
     vaccine:string;
     manufacturer:string;//ok
     doctor:string;//ok
     nurse:string;//ok
}

export const getAllBonuses=(tokenData: TokenData)=>{
    console.log('recupero bonus')
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/bonusAPI/v1/bonuses/'+ tokenData.email+'/?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}

export const getAllVaccines=(tokenData: TokenData)=>{
    console.log('recupero vaccini')
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/vaccinationAPI/v1/vaccinations/'+ tokenData.email+'/?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    })
}