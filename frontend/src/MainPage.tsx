import {
    Card,
    CardActionArea,
    CardContent,
    CardMedia,
    createTheme,
    Divider,
    Grid,
    IconButton,
    Typography,
    ThemeProvider
} from "@mui/material";
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import React from "react";
import './App.css';
import vote from "./imgs/vote.png"
import personalData from "./imgs/personaldata.png"
import iniziative from "./imgs/iniziative.png"
import "@fontsource/ubuntu-mono";


const MainPage = () => {
    const theme = createTheme({
            typography: {
                fontFamily: [
                    'ubuntu'
                ].join(','),
            },
        }
    );

    return (
        <ThemeProvider theme={theme}>
        <Grid container className="App-header">
            <Grid container direction="row">
                <Grid item xs={12} display="flex">
                    <IconButton><KeyboardBackspaceIcon sx={{fontSize: 60, color: '#ffffff'}}/></IconButton>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <Typography style={{color: 'white', textAlign: 'center', fontSize: '3rem'}}>BENVENUTO
                        USER</Typography>
                </Grid>
            </Grid>
            <Grid container direction="row" spacing={2}>
                <Grid item xs={6} display="flex" justifyContent="center" alignItems="center">
                    <Card sx={{width: '60%'}}>
                        <CardActionArea>
                            <Grid container direction="column">
                                <Grid item display="flex" justifyContent="center" alignItems="center"
                                      style={{backgroundColor: '#d4e3fc'}}>
                                    <CardMedia
                                        component="img"
                                        image={personalData}
                                        sx={{
                                            width: '60%',

                                        }}
                                        alt="personalData"
                                    />
                                </Grid>
                                <Divider style={{marginTop: '5px'}}/>
                                <Grid item display="flex" justifyContent="center" alignItems="center">
                                    <CardContent>
                                        <Typography gutterBottom variant="h5" component="div">
                                            I tuoi Dati
                                        </Typography>
                                    </CardContent>
                                </Grid>
                            </Grid>
                        </CardActionArea>
                    </Card>
                </Grid>
                <Grid item xs={6} display="flex" justifyContent="center" alignItems="center">
                    <Card sx={{width: '60%'}}>
                        <CardActionArea>
                            <Grid container direction="column">
                                <Grid item display="flex" justifyContent="center" alignItems="center"
                                      style={{backgroundColor: '#f1f6be'}}>
                                    <CardMedia
                                        component="img"
                                        image={iniziative}
                                        sx={{
                                            width: '70%',
                                        }}
                                        alt="iniziative"
                                    />
                                </Grid>
                                <Divider style={{marginTop: '5px'}}/>
                                <Grid item display="flex" justifyContent="center" alignItems="center">
                                    <CardContent>
                                        <Typography gutterBottom variant="h5" component="div">
                                            Iniziative
                                        </Typography>
                                    </CardContent>
                                </Grid>
                            </Grid>
                        </CardActionArea>
                    </Card>
                </Grid>
            </Grid>
            <Grid container>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <Card sx={{width: '30%'}}>
                        <CardActionArea>
                            <Grid container direction="column">
                                <Grid item display="flex" justifyContent="center" alignItems="center"
                                      style={{backgroundColor: '#ff5d55'}}>
                                    <CardMedia
                                        component="img"
                                        image={vote}
                                        sx={{
                                            width: '45%',
                                        }}
                                        alt="vote"
                                    />
                                </Grid>
                                <Divider style={{marginTop: '5px'}}/>
                                <Grid item display="flex" justifyContent="center" alignItems="center">
                                    <CardContent>
                                        <Typography gutterBottom variant="h5" component="div">
                                            Votazioni
                                        </Typography>
                                    </CardContent>
                                </Grid>
                            </Grid>
                        </CardActionArea>
                    </Card>
                </Grid>

            </Grid>
        </Grid>
        </ThemeProvider>
    );

}

export default MainPage;