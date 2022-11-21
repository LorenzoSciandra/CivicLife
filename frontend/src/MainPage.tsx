import {
    Button,
    Grid,
    Typography,
    IconButton,
    CardContent,
    CardMedia,
    CardActionArea,
    Card,
    Divider
} from "@mui/material";
import KeyboardBackspaceIcon from '@mui/icons-material/KeyboardBackspace';
import React from "react";
import './App.css';
import vote from "./imgs/vote.png"
import personalData from "./imgs/personaldata.png"
import iniziative from "./imgs/iniziative.png"


const MainPage = () => {


    return (
        <Grid container className="App-header">
            <Grid container direction="row" spacing={1}>
                <Grid item xs={3} display="flex" justifyContent="center" alignItems="center">
                    <IconButton><KeyboardBackspaceIcon sx={{ fontSize: 60 }}/></IconButton>
                </Grid>
                <Grid item xs={9} display="flex" justifyContent="center" alignItems="center">
                    <Typography style={{color: 'white', textAlign: 'center', fontSize: '2rem'}}>BENVENUTO USER</Typography>
                </Grid>
            </Grid>
            <Grid container direction="row" spacing={2}>
                <Grid item xs={6} display="flex" justifyContent="center" alignItems="center">
                    <Card sx={{ width: '60%'}}>
                        <CardActionArea>
                            <Grid container direction="column">
                                <Grid item display="flex" justifyContent="center" alignItems="center" style={{backgroundColor: '#d4e3fc'}}>
                                    <CardMedia
                                        component="img"
                                        image={personalData}
                                        sx={{
                                            width:'60%',

                                        }}
                                        alt="green iguana"
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
                <Grid item xs={6} display="flex" justifyContent="center" alignItems="center" >
                    <Card sx={{ width: '60%'}}>
                        <CardActionArea>
                            <Grid container direction="column">
                                <Grid item display="flex" justifyContent="center" alignItems="center" style={{backgroundColor: '#f1f6be'}}>
                                    <CardMedia
                                        component="img"
                                        image={iniziative}
                                        sx={{
                                            width:'70%',
                                        }}
                                        alt="green iguana"
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
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center" >
                    <Card sx={{ width: '30%'}}>
                        <CardActionArea>
                            <Grid container direction="column">
                                <Grid item display="flex" justifyContent="center" alignItems="center" style={{backgroundColor: '#ff5d55'}}>
                                    <CardMedia
                                        component="img"
                                        image={vote}
                                        sx={{
                                            width:'45%',
                                        }}
                                        alt="green iguana"
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
    );

}

export default MainPage;