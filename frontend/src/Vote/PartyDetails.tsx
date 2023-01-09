import {
    Button,
    Card,
    CardActionArea,
    CardActions,
    CardContent,
    CardMedia,
    Grid,
    IconButton,
    Typography
} from "@mui/material";
import KeyboardBackspaceIcon from "@mui/icons-material/KeyboardBackspace";
import Box from "@mui/material/Box";


import React, {useState} from "react";

import '../App.css'
import personalData from "../imgs/personaldata.png"
import {useLocation, useNavigate} from "react-router-dom";

const PartyDetails = () => {

    const [candidateList, setCandidateList] = useState(['candidato1', 'candidato2', 'candidato3', 'candidato4', 'candidato1', 'candidato2', 'candidato3', 'candidato4', 'candidato1', 'candidato2', 'candidato3', 'candidato4', 'candidato1', 'candidato2', 'candidato3', 'candidato4'])
    const location= useLocation()
    const navigate= useNavigate()
    const party= location.state?.party

    const handleCandidateDetailsOpen=(value:any)=>{
        navigate('/votations/votationDetails/partyDetails/candidateDetails',{state:{state: {token: location.state?.token, email: location.state?.email, isAdmin: location.state?.isAdmin, votation:location.state?.votation, party:location.state?.party, candidate:value}}})
    }

    const partyCards = candidateList.map((value, index) => {
        return (
            <>
                <Card sx={{maxWidth: 345, margin: '20px'}}>
                    <CardActionArea onClick={()=>{handleCandidateDetailsOpen(value)}}>
                        <CardMedia
                            component="img"
                            height="140"
                            image={personalData}
                            alt={value}
                        />
                        <CardContent>
                            <Typography gutterBottom variant="h5" component="div">
                                {value}
                            </Typography>
                            <Typography variant="body2" color="text.secondary">
                                Descrizione breve del candidato
                                Impegnato nel sociale e attivo nella comunità
                            </Typography>
                        </CardContent>
                    </CardActionArea>
                    <CardActions style={{alignItems: 'center'}}>
                        <Button size="large" style={{color: '#ff3823', width: '100%'}}>Vota Candidato</Button>
                    </CardActions>
                </Card>

            </>
        );
    })
    const styleForFlexboxWithoutScrollbar = {
        margin: 0,
        width: "100%",
    };
    return (
        <Grid container direction='row' spacing={5}>
            {/*<Grid item xs={12} display="flex" sx={{*/}
            {/*    width: '100%',*/}
            {/*    margin: "auto",*/}
            {/*    top: 0,*/}
            {/*    right: 0*/}
            {/*}}>*/}
            {/*    <IconButton><KeyboardBackspaceIcon sx={{fontSize: 60, color: '#ffffff'}}/></IconButton>*/}
            {/*</Grid>*/}
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{
            width: '100%',
            margin: "auto",
            top: 150,
            right: 0
        }}>
            <Typography style={{color: '#feac0d', textAlign: 'center', fontSize: '3rem'}}>{party}</Typography>
        </Grid><Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{
            width: '100%',
            margin: "auto",
            top: 65,
            right: 0
        }}>
            <Typography style={{
                color: '#ffffff',
                textAlign: 'justify',
                fontSize: '0.95rem',
                paddingLeft: '120px',
                paddingRight: '120px'
            }}>orem ipsum dolor sit amet,
                consectetur adipiscing elit. Curabitur quis interdum augue, et euismod lectus. Ut ac ultricies augue.
                Praesent in ornare elit, a pellentesque libero. Nam euismod mi nec tortor elementum aliquet. Curabitur
                dictum mi quis rutrum ultricies. Maecenas in diam ut mauris venenatis consectetur. Quisque eu neque ac
                lacus laoreet tincidunt. Mauris vitae condimentum odio, nec cursus nisi. Sed efficitur ante eu iaculis
                venenatis. Ut in dictum ante. Nunc eget bibendum ex. Suspendisse facilisis luctus nunc sed auctor. Nunc
                tincidunt egestas enim, nec condimentum urna laoreet quis. Praesent commodo lacus in turpis maximus
                sodales.

                Nunc velit lectus, ultricies non consequat nec, egestas lobortis lectus. Sed vitae imperdiet augue, eget
                pharetra arcu. Pellentesque fringilla magna non nisi euismod, ac feugiat sapien consectetur. Aliquam
                imperdiet fermentum scelerisque. Nam tristique tincidunt ipsum, eget pharetra orci hendrerit sed.
                Interdum et malesuada fames ac ante ipsum primis in faucibus. Nunc molestie semper lorem et vulputate.
                Aliquam convallis ex sit amet eleifend pellentesque.ondimentum odio, nec cursus nisi. Sed efficitur ante
                eu iaculis venenatis. Ut in dictum ante. Nunc eget bibendum ex. Suspendisse facilisis luctus nunc sed
                auctor. Nunc tincidunt egestas enim, nec condimentum urna laoreet quis. Praesent commodo lacus in turpis
                maximus sodales.

                Nunc velit lectus, ultricies non consequat nec, egestas lobortis lectus. Sed vitae imperdiet augue, eget
                pharetra arcu. Pellentesque fringilla magna non nisi euismod, ac feugiat sapien consectetur. Aliquam
                imperdiet fermentum scelerisque. Nam tristique tincidunt ipsum, eget pharetra orci hendrerit sed.
                Interdum et malesuada fames ac ante ipsum primis in faucibus. Nunc molestie semper lorem et vulputate.
                Aliquam convallis ex sit amet eleifend pellentesque.</Typography>
        </Grid>
            <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                <Button style={{
                    borderRadius: 35,
                    backgroundColor: "#ff3823",
                    padding: "10px 20px",
                    fontSize: "18px"
                }}
                        variant="contained">
                    VOTA Partito
                </Button>
            </Grid>
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{
            width: '100%',
            margin: "auto",
            top: 280, right: 0
        }}>
            <Grid container direction="column" justifyContent="center" alignItems="center">
                <Box sx={{width: '70%', height: '100%'}}>
                    <Grid
                        container
                        spacing={1}
                        alignContent="center"
                        wrap="wrap"
                        style={styleForFlexboxWithoutScrollbar}
                    >
                        {partyCards}
                    </Grid>
                </Box>
            </Grid>
        </Grid>
        </Grid>
    );
}

export default PartyDetails;