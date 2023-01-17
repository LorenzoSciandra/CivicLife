import axios from "axios";
import {AuthError, TokenData, ValidateCode} from "./OauthAPI";
import {Base64} from "js-base64";

export enum InitiativeStatus {
    PROGRAMMED = 'PROGRAMMED',
    IN_PROGRESS = 'IN_PROGRESS',
    FINISHED = 'FINISHED',
}

export enum InitiativeType {
    SOCIAL = 'SOCIAL',
    CULTURAL = 'CULTURAL',
    HEALTH = 'HEALTH',
    SPORT = 'SPORT',
    EDUCATIONAL = 'EDUCATIONAL',
    ENVIRONMENTAL = 'ENVIRONMENTAL',
    OTHER = 'OTHER'
}

export interface Initiative{
     name: string,
     description: string;
     status: InitiativeStatus;
     type: InitiativeType;
     idCreator: string;
     idOrganizers: string[];
     idMembers: string[];
     startDate: number;
     endDate: number;
     location: string;
}

export const getAllInitiatives = async (): Promise<Initiative[]| AuthError> => {
    const url = 'http://localhost:8080/initiativeAPI/v1/initiatives'
    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath:url
        }
        return authError
    })
}

export const createInitiative = async (tokenData: TokenData, initiative: Initiative) => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.post(url, initiative).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(function (error) {
        console.log(error)
        const authError: AuthError = {
            code: ValidateCode.UPDATE_FAIL,
            method: 'POST',
            requestedPath: url.split('?')[0]
        }
        return authError
    });
}