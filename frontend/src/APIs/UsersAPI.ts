import {AuthError, TokenData, ValidateCode} from "./OauthAPI";
import axios from "axios";
import {Base64} from "js-base64";

export enum UserStatus{
    ACTIVE='ACTIVE', // puo fare tutto
    SUSPENDED='SUSPENDED', // puo fare tutto tranne creare iniziative
    BANNED='BANNED' // puo solo votare le iniziative sono bloccate
}

export enum UserStatusColor{
    ACTIVE='#4CAF50',
    SUSPENDED='#FFC107',
    BANNED='#F44336'
}

export interface User{
    email: string,
    name: string,
    surname: string,
    admin: boolean,
    fiscalCode:string,
    residence:string,
    birthDate:number,
    domicile:string,
    status:UserStatus,
    telephonNumber: number,
    authorizeBonus: boolean,
    authorizeVaccine: boolean,
}

export const getLoggedUser = (tokenData: TokenData) : Promise<User | AuthError>=> {
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/userAPI/v1/user/get/' + tokenData.email+ '?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return axios.get<User | AuthError>(url).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
            const authError: AuthError = {
                code: ValidateCode.LOGIN_FAIL,
                method: 'GET',
                requestedPath: url.split('?')[0]
            }
            return authError
        }
    )
}

export function isInstanceOfUser(object:any): object is User {
    return 'email' in object
}


export const updateUser=async (tokenData: TokenData, newUser: User): Promise<boolean | AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/userAPI/v1/user/update/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.post<boolean| AuthError>(url,
        JSON.stringify(newUser),
        {
            headers: {"Content-Type": "text/plain"}
        })
        .then(value => {
            return value.data;
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

export const authorizeBonusAccess=async (tokenData: TokenData): Promise<boolean | AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/userAPI/v1/user/authorizeBonus/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
            const authError: AuthError = {
                code: ValidateCode.UPDATE_FAIL,
                method: 'GET',
                requestedPath: url.split('?')[0]
            }
            return authError
        })
}

export const authorizeVaccineAccess=async (tokenData: TokenData): Promise<boolean | AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/userAPI/v1/user/authorizeVaccine/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.UPDATE_FAIL,
            method: 'GET',
            requestedPath: url.split('?')[0]
        }
        return authError
    })
}

export const getAllUsersEmail=async (tokenData: TokenData): Promise<string[] | AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/userAPI/v1/users/emails/' +tokenData.email +'?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.UPDATE_FAIL,
            method: 'GET',
            requestedPath: url.split('?')[0]
        }
        return authError
    })
}


//ADMIN METHODS

export const updateUserStatus = async (tokenData:TokenData, newStatus:UserStatus, emailToUpdate:string): Promise<boolean | AuthError>=>{
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/userAPI/v1/user/update/status/' + emailToUpdate +'/'+ tokenData.email+ '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    console.log(url)
    return await axios.post<boolean | AuthError>(url,
        Base64.encode(newStatus),
        {
            headers: {"Content-Type": "text/plain"}
        })
        .then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
            const authError: AuthError = {
                code: ValidateCode.UPDATE_FAIL,
                method: 'POST',
                requestedPath: url.split('?')[0]
            }
            return authError
        })
}

export const getAllUsers = (tokenData: TokenData): Promise<User[] | AuthError> => {
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/userAPI/v1/users/'+tokenData.email+'?email='+ emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return axios.get<User[] | AuthError>(url).then((response) => {
        console.log('response', response.data)
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


