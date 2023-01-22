import {AppBar, Button, Card, CardMedia, Grid, IconButton, Typography} from "@mui/material";
import React from "react";
import personalData from "../imgs/personaldata.png";
import {useLocation, useNavigate} from "react-router-dom";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {logoutUser} from "../APIs/OauthAPI";

const CandidateDetails = () => {
    const location = useLocation()
    const navigate = useNavigate()
    const candidate = location.state.candidate
    const tokenData = location.state.token
    const isVisitor = location.state.isVisitor
    const user = location.state.user
    const votation = location.state.votation

    const logout = async () => {
        if (tokenData !== null) {
            const response = await logoutUser(tokenData)
            if (typeof response === 'boolean') {
                if (response) {
                    navigate('/')
                } else {
                    console.log('error')
                }
            } else {
                navigate('/error', {state: {error: response}})
            }
        }
    }


    const goBack = () => {
        navigate(-1)
    }

    const login = () => {
        window.location.assign('http://localhost:8080/login')
    }
    return (
        <>
            <Grid container direction='row' spacing={5}>
                <Box sx={{flexGrow: 1}}>
                    <AppBar position="fixed" sx={{backgroundColor: '#3d4347'}}>
                        <Toolbar>
                            <IconButton
                                size="small"
                                edge="start"
                                aria-label="menu"
                                sx={{mr: 2}}
                            >
                                <ArrowBackIcon onClick={goBack} sx={{fontSize: '3rem', color: 'white'}}/>
                            </IconButton>

                            <Typography variant="h6" component="div" sx={{flexGrow: 1}}
                                        style={{
                                            justifyContent: 'center',
                                            color: '#feac0d',
                                            textAlign: 'center',
                                            fontSize: '1.8rem',
                                        }}>
                                {candidate.name} {candidate.surname}
                            </Typography>
                            <Button
                                onClick={isVisitor ? login : logout}
                                style={{
                                    color: 'white',
                                    backgroundColor: isVisitor ? "green" : "red",
                                }}>

                                {isVisitor ? "login" : "logout"}
                            </Button>
                        </Toolbar>
                    </AppBar>
                </Box>
            </Grid>
            <Grid container direction='row' alignItems="center" justifyContent="flex-start" spacing={12}>

                <Grid item xs={12} display="flex" alignItems="center" justifyContent="center">
                    <Card>
                        <CardMedia
                            height="300"
                            width="300"
                            component="img"
                            image={candidate.imageLink ? candidate.imageLink : personalData}
                            alt={candidate.name}/>
                    </Card>
                </Grid>
                <Grid item xs={12}>
                    <Typography style={{
                        color: '#ffffff',
                        textAlign: 'justify',
                        fontSize: '1rem',
                        paddingLeft: '20px',
                        paddingRight: '20px'
                    }}> {candidate.description}</Typography>
                </Grid>
                {user && user.admin ? null : user ?
                    <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                        <Button style={{
                            marginTop: '15px',
                            borderRadius: 35,
                            backgroundColor: "#ff3823",
                            padding: "18px 36px",
                            fontSize: "15px"
                        }}
                                variant="contained">
                            VOTA candidato
                        </Button>
                    </Grid> : null}

            </Grid>
        </>
    );
}
export default CandidateDetails