import {Divider, Grid, IconButton, Typography} from "@mui/material";
import KeyboardBackspaceIcon from "@mui/icons-material/KeyboardBackspace";
import Box from "@mui/material/Box";

import React, {useState} from "react";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import '../App.css'
import {useLocation, useNavigate} from "react-router-dom";


const VotationDetails=()=>{
    const [partyList, setPatyList ]=useState(['partito1', 'partito2', 'partito3', 'partito4','partito1', 'partito2', 'partito3', 'partito4','partito1', 'partito2', 'partito3', 'partito4','partito1', 'partito2', 'partito3', 'partito4'])
    const location= useLocation()
    const navigate= useNavigate()
    const votation= location.state?.votation

    const handlePartyDetailsOpen = (value: any) => {
        navigate('/votations/votationDetails/partyDetails', {state: {token: location.state?.token, email: location.state?.email, isAdmin: location.state?.isAdmin, votation:location.state?.votation, party:value}})
    }

    return(
        <Grid container direction='row' spacing={5}>
        {/*    <Grid item xs={12} display="flex" sx={{*/}
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
            <Typography style={{color: '#feac0d', textAlign: 'center', fontSize: '3rem'}}>{votation}</Typography>
        </Grid><Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{
            width: '100%',

            margin: "auto",
            top: 65,
            right: 0
        }}>
            <Typography style={{color: '#ffffff', textAlign: 'justify', fontSize: '0.95rem', paddingLeft:'120px', paddingRight:'120px'}}>orem ipsum dolor sit amet,
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
        </Grid><Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{
            width: '100%',
            margin: "auto",
            top: 280, right: 0
        }}>
            <Box sx={{width: '70%', height: '100%', paddingBottom:'70px'}}>
                {partyList.map((value, index) => {
                    return (
                        <><
                            ListItem key={index}>
                            <ListItemButton onClick={()=>{handlePartyDetailsOpen(value)}}>{value}</ListItemButton>
                        </ListItem>
                            <Divider color='white'/>
                        </>
                    );
                })}
            </Box>
        </Grid>
        </Grid>
    );

}

export default VotationDetails;