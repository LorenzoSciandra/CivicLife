import React, {useEffect, useState} from "react";
import {Button, Grid, styled, TableBody, TableContainer, TableHead, Typography} from "@mui/material";
import {useLocation, useNavigate} from "react-router-dom";
import Table from '@mui/material/Table';
import TableCell, {tableCellClasses} from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import CancelTwoToneIcon from '@mui/icons-material/CancelTwoTone';
import {AuthError, ValidateCode} from "./APIs/OauthAPI";

const Error = () => {
    const location = useLocation()
    const error = location.state.error
    const navigate = useNavigate()
    const [errorReason, setErrorReason] = useState<AuthError|null>(null)
    const [infoList, setInfoList] = useState<string[]>([])

    useEffect(() => {
        if (infoList.length === 0 && error!==undefined) {
            console.log(error)
            setErrorReason(error)
            splittedRequest(error)
        }
        else if (window.location.href.includes('errorReason=')) {
                const errorReason = window.location.href.split("errorReason=")[1].toString()
                const authError= generateErrorReason(errorReason)
                setErrorReason(authError)
                splittedRequest(authError)
        } else{
            navigate('/')
        }
    },[])

    const StyledTableCell = styled(TableCell)(({theme}) => ({
        [`&.${tableCellClasses.head}`]: {
            backgroundColor: theme.palette.common.black,
            color: theme.palette.common.white,
        },
        [`&.${tableCellClasses.body}`]: {
            fontSize: 14,
        },
    }));

    const generateErrorReason = (errorReason:string) => {
        const path = errorReason.split('requestedPath=')[1].split(',')[0]
        const method = errorReason.split('method=')[1].split(',')[0]
        const authError : AuthError = {
            code: ValidateCode.AUTH_SERVER_ERROR,
            requestedPath: path,
            method: method
        }
        return authError

    }

    const splittedRequest = (errorReason:AuthError) => {
        if(errorReason!==null) {
            const path = errorReason.requestedPath.split('/')
            const returnValue = []
            let pathtoprint = ''
            for (let value = 0; value < path.length; value++) {
                if (path[value] !== '') {
                    if (value === 2) {
                        let split = path[value].split(':')
                        returnValue.push(split[0])
                        returnValue.push(split[1])

                    }
                    if (value === 3) {
                        returnValue.push(path[value])
                    }
                    if (value > 4) {
                        pathtoprint += path[value] + '/'
                    }
                }
            }
            returnValue.push(pathtoprint)

            setInfoList(returnValue)
        }
    }

    return (
        <Grid container direction={'column'} spacing={3}>
            <Grid item display="flex" justifyContent="center" alignItems="center">
                <CancelTwoToneIcon style={{color: 'red', fontSize: '7rem'}}/>
            </Grid>
            <Grid item display="flex" justifyContent="center" alignItems="center">
                <Typography variant={'h2'}>
                    ATTENZIONE
                </Typography>
            </Grid>
            {
                infoList.length > 3 && errorReason ?

            <Grid item display="flex" justifyContent="center" alignItems="center">
                <TableContainer sx={{backgroundColor:'white', width:'90%'}}>
                    <Table aria-label='customized-table'>
                        <TableHead>
                            <TableRow>
                                <StyledTableCell>info</StyledTableCell>
                                <StyledTableCell align='right'>value</StyledTableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            <TableRow>
                                <StyledTableCell component='th' scope='row'>Error code</StyledTableCell>
                                <StyledTableCell align='right'>{errorReason.code}</StyledTableCell>
                            </TableRow>
                            <TableRow>
                                <StyledTableCell component='th' scope='row'>Method</StyledTableCell>
                                <StyledTableCell align='right'>{errorReason.method}</StyledTableCell>
                            </TableRow>
                            <TableRow>
                                <StyledTableCell component='th' scope='row'>IP</StyledTableCell>
                                <StyledTableCell align='right'>{infoList[0]}</StyledTableCell>
                            </TableRow>
                            <TableRow>
                                <StyledTableCell component='th' scope='row'>Port</StyledTableCell>
                                <StyledTableCell align='right'>{infoList[1]}</StyledTableCell>
                            </TableRow>
                            <TableRow>
                                <StyledTableCell component='th' scope='row'>Micro Service</StyledTableCell>
                                <StyledTableCell align='right'>{infoList[2]}</StyledTableCell>
                            </TableRow>
                            <TableRow>
                                <StyledTableCell component='th' scope='row'>Api</StyledTableCell>
                                <StyledTableCell align='right'>{infoList[3]}</StyledTableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
            </Grid>: null
            }
            <Grid item display="flex" justifyContent="center" alignItems="center">
                <Button style={{
                    borderRadius: 35,
                    backgroundColor: "red",
                    padding: "10px 20px",
                    fontSize: "18px"
                }}
                        variant="contained"
                        onClick={() => {
                            navigate('/')
                        }}>
                    torna al login
                </Button>
            </Grid>
        </Grid>
    );
}

export default Error;