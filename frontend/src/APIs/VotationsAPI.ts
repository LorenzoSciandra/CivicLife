import {AuthError, TokenData, ValidateCode} from "./OauthAPI";
import axios from "axios";
import {Base64} from "js-base64";

export interface CandidateResult {
    candidateId: string,
    votes: number,
    name: string,
    surname: string,
    percentage: number,
    isLeader: boolean
}

export interface PartyResult {
    partyId: string,
    votes: number,
    percentage:number,
    candidateResults: CandidateResult[]
}

export interface VotationResult {
    numberOfVotes: number,
    votersIdList: string[],
    partyResults: PartyResult[],
}

export interface Party {
    name: string,
    logoLink: string,
    description: string,
    candidateIdList: string[],
    leaderId: string,
}

export enum VotationStatus {
    ACTIVE = "ACTIVE",
    TERMINATED = "TERMINATED",
    PROGRAMMED = "PROGRAMMED"
}

export interface Candidate {
    id: string,
    imageLink: string,
    partyId: string,
    name: string,
    surname: string,
    description: string,
}

export interface Votation {
    title: string,
    description: string,
    startDate: number,
    endDate: number,
    status: VotationStatus,
    parties: string[],
    votationResult: VotationResult
}


export const getActiveVotations = async (): Promise<Votation[] | AuthError> => {
    const url = 'http://localhost:8080/votationAPI/v1/votations/active'
    return await axios.get(url).then((response) => {
        // console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}

export const getEndedVotations = async (): Promise<Votation[] | AuthError> => {
    const url = 'http://localhost:8080/votationAPI/v1/votations/terminated'
    return await axios.get(url).then((response) => {
        // console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}

export const getProgrammedVotations = async (tokenData:TokenData): Promise<Votation[] | AuthError> => {
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/votationAPI/v1/votations/programmed/'+ tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return await axios.get(url).then((response) => {
        // console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}

export const getDoneVotations = async (tokenData:TokenData): Promise<Votation[] | AuthError> => {
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/votationAPI/v1/votations/done/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return await axios.get(url).then((response) => {
        // console.log('response done', response.data)
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

export const getParties = async (votationId: string): Promise<Party[] | AuthError> => {
    const base64VotationId = Base64.encode(votationId)
    const url = 'http://localhost:8080/votationAPI/v1/votation/get/parties/' + base64VotationId
    return await axios.get(url).then((response) => {
        // console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}

export const getCandidates= async (partyId: string): Promise<Candidate[] | AuthError> => {
    const base64PartyId = Base64.encode(partyId)
    const url = 'http://localhost:8080/partyAPI/v1/party/get/allCandidates/' + base64PartyId
    return await axios.get(url).then((response) => {
        // console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}

export const changeVotationStatus = async (tokenData: TokenData,votationId: string, status: VotationStatus): Promise<AuthError | boolean> => {
    const base64VotationId = Base64.encode(votationId)
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/votationAPI/v1/votation/updateStatus/' + base64VotationId+'/'+tokenData.email+'?email='+emailBase64+'&provider='+providerBase64+'&token='+tokenBase64;
    return await axios.post(url,
        status,
        {
            headers: {
                'Content-Type': 'text/plain',
            }
        }).then((response) => {
        // console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'POST',
            requestedPath: url.split('?')[0]
        }
        return authError
    })
}

export const voteForParty = async (tokenData: TokenData,votationId: string, partyId: string): Promise<AuthError | boolean> => {
    const base64VotationId = Base64.encode(votationId)
    const base64PartyId = Base64.encode(partyId)
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/votationAPI/v1/votation/voteParty/' + base64VotationId + '/' + base64PartyId + '/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return await axios.get(url).then((response) => {
        // console.log('response', response.data)
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

export const voteForCandidate = async (tokenData: TokenData,votationId: string, partyId: string, candidateId: string): Promise<AuthError | boolean> => {
    const base64VotationId = Base64.encode(votationId)
    const base64PartyId = Base64.encode(partyId)
    const emailBase64= Base64.encode(tokenData.email)
    const providerBase64= Base64.encode(tokenData.provider)
    const tokenBase64= Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/votationAPI/v1/votation/voteCandidate/'+ base64VotationId + '/' + base64PartyId + '/' + candidateId+ '/' +tokenData.email+ '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return await axios.get(url).then((response) => {
        // console.log('response', response.data)
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


