import axios from 'axios';
import {AuthError, TokenData, ValidateCode} from "./OauthAPI";
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
     email_owner:string;
     date:number;
     location:string;
     description:string;
     vaccineName:string;
     dose:string;
     manufacturer:string;
     doctor:string;
     nurse:string;
}

export const getAllBonuses=(tokenData: TokenData): Promise<BonusType[]|AuthError>=>{
    console.log('recupero bonus')
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/bonusAPI/v1/bonuses/'+ tokenData.email+'/?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return axios.get(url).then((response) => {
        // console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url.split('?')[0]
        }
        return authError
    })
}

export const getAllVaccines=(tokenData: TokenData): Promise<VaccineType[]|AuthError>=>{
    console.log('recupero vaccini')
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/vaccinationAPI/v1/vaccinations/'+ tokenData.email+'/?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return axios.get(url).then((response) => {
        // console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url.split('?')[0]
        }
        return authError
    })
}