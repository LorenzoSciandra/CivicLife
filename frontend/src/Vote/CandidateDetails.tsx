import {Button, Card, CardMedia, Grid, Typography} from "@mui/material";
import React from "react";
import personalData from "../imgs/personaldata.png";
import {useLocation} from "react-router-dom";

const CandidateDetails = () => {
    const location= useLocation()
    const candidate= location.state?.candidate
    const isAdmin= location.state?.isAdmin

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
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <Typography style={{color: '#feac0d', textAlign: 'center', fontSize: '3rem'}}>{candidate}</Typography>
            </Grid>
            <Grid item xs={12} display="flex" alignItems="center" justifyContent="center">
                <Card >
                        <CardMedia
                            height="300"
                            width="300"
                            component="img"
                            image={personalData}
                            alt={candidate}
                        />
                </Card>
            </Grid>
            <Grid item xs={12}>
                <Typography style={{
                    color: '#ffffff',
                    textAlign: 'justify',
                    fontSize: '1rem',
                    paddingLeft: '20px',
                    paddingRight: '20px'
                }}> orem ipsum dolor sit amet,
                    consectetur adipiscing elit. Curabitur quis interdum augue, et euismod lectus. Ut ac ultricies
                    augue.
                    Praesent in ornare elit, a pellentesque libero. Nam euismod mi nec tortor elementum aliquet.
                    Curabitur
                    dictum mi quis rutrum ultricies. Maecenas in diam ut mauris venenatis consectetur. Quisque eu neque
                    ac
                    lacus laoreet tincidunt. Mauris vitae condimentum odio, nec cursus nisi. Sed efficitur ante eu
                    iaculis
                    venenatis. Ut in dictum ante. Nunc eget bibendum ex. Suspendisse facilisis luctus nunc sed auctor.
                    Nunc
                    tincidunt egestas enim, nec condimentum urna laoreet quis. Praesent commodo lacus in turpis maximus
                    sodales.

                    Nunc velit lectus, ultricies non consequat nec, egestas lobortis lectus. Sed vitae imperdiet augue,
                    eget</Typography>
            </Grid>
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{
                width: '100%',

                margin: "auto",
                top: 65,
                right: 0
            }}>
                <Typography style={{
                    color: '#ffffff',
                    textAlign: 'justify',
                    fontSize: '1rem',
                    paddingLeft: '20px',
                    paddingRight: '20px'
                }}>orem ipsum dolor sit amet,
                    consectetur adipiscing elit. Curabitur quis interdum augue, et euismod lectus. Ut ac ultricies
                    augue.
                    Praesent in ornare elit, a pellentesque libero. Nam euismod mi nec tortor elementum aliquet.
                    Curabitur
                    dictum mi quis rutrum ultricies. Maecenas in diam ut mauris venenatis consectetur. Quisque eu neque
                    ac
                    lacus laoreet tincidunt. Mauris vitae condimentum odio, nec cursus nisi. Sed efficitur ante eu
                    iaculis
                    venenatis. Ut in dictum ante. Nunc eget bibendum ex. Suspendisse facilisis luctus nunc sed auctor.
                    Nunc
                    tincidunt egestas enim, nec condimentum urna laoreet quis. Praesent commodo lacus in turpis maximus
                    sodales.

                    Nunc velit lectus, ultricies non consequat nec, egestas lobortis lectus. Sed vitae imperdiet augue,
                    eget
                    pharetra arcu. Pellentesque fringilla magna non nisi euismod, ac feugiat sapien consectetur. Aliquam
                    imperdiet fermentum scelerisque. Nam tristique tincidunt ipsum, eget pharetra orci hendrerit sed.
                    Interdum et malesuada fames ac ante ipsum primis in faucibus. Nunc molestie semper lorem et
                    vulputate.
                    Aliquam convallis ex sit amet eleifend pellentesque.ondimentum odio, nec cursus nisi. Sed efficitur
                    ante
                    eu iaculis venenatis. Ut in dictum ante. Nunc eget bibendum ex. Suspendisse facilisis luctus nunc
                    sed</Typography>
            </Grid>
            {   isAdmin ? null : <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
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
            </Grid>

            }

        </Grid>
    );
}
export default CandidateDetails