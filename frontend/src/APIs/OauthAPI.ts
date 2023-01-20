import axios from 'axios';
import {Base64} from "js-base64";
import {User} from "./UsersAPI";

export type TokenData= {
    email:string,
    provider:string,
    token:string,
}

export enum ValidateCode{
    ACTIVE = 'ACTIVE',
    EXPIRED = 'EXPIRED',
    INVALID_EMAIL = 'INVALID_EMAIL',
    INVALID_TOKEN = 'INVALID_TOKEN',
    INVALID_PROVIDER = 'INVALID_PROVIDER',
    INCOMPLETE_CREDENTIALS = 'INCOMPLETE_CREDENTIALS',
    AUTH_SERVER_ERROR = 'AUTH_SERVER_ERROR',
    UPDATE_FAIL = 'UPDATE_FAIL',
    LOGOUT_FAIL = 'LOGOUT_FAIL',
    LOGIN_FAIL = 'LOGIN_FAIL',
    GET_FAIL = 'GET_FAIL',
    DELETE_FAIL = 'DELETE_FAIL',
}

export interface AuthError{
    code: ValidateCode,
    method: string,
    requestedPath: string
}

export function isInstanceOfAuthError(object:any): object is AuthError {
    return 'code' in object
}

export const exchangeToken = async (token: string):Promise<TokenData|AuthError>=> {
    console.log('qui ho questo',token)
    let url = 'http://localhost:8080/authAPI/v1/token/'+ token
    return await axios.get(url).then((response) => {
        const data = Base64.decode(response.data);
        const values = data.split(",")
        console.log('values trovati',values)
        for(let i = 0; i < values.length; i++){
            values[i] = values[i].split(": ")[1]
        }
        return{
            email: values[0],
            provider: values[1],
            token: values[2],
        }
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.LOGIN_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}

export const logoutUser = async (tokenData: TokenData): Promise<boolean | AuthError> => {

    const TokenKey= {
        email: tokenData.email,
        provider: tokenData.provider,
    }

    const url = 'http://localhost:8080/authAPI/v1/deleteToken/' + tokenData.email
    return await axios.post<boolean| AuthError>(url,
        JSON.stringify(TokenKey),
        {
            headers: {"Content-Type": "text/plain"}
        })
        .then(value => {
            return value.data;
        }).catch(() => {
            const authError: AuthError = {
                code: ValidateCode.LOGOUT_FAIL,
                method: 'POST',
                requestedPath: url.split('?')[0]
            }
            return authError
        })
}

