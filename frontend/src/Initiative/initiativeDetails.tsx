import {Button, CardMedia, Grid, IconButton, Typography,} from "@mui/material";

import KeyboardBackspaceIcon from "@mui/icons-material/KeyboardBackspace";
import '../App.css'
import React, {useState} from "react";
import Box from '@mui/material/Box';
import basquiaPulito from "../imgs/logo_CivicLife.png"
import {CssTextField} from "../Utils/CustomTextFields";

type InitiativeDetailspropsType={
    initiative:any,
    handleClose: ()=> void,
}
const InitiativeDetails=(props: InitiativeDetailspropsType)=>{
    const [value, setValue]= useState(props.initiative)

    const handleChange=(event:React.ChangeEvent<HTMLInputElement>)=>{
        setValue(event.target.value)
    }

    console.log('inizio')
    console.log('ciao')

    return (
            <Grid container className="App-header">
                <Grid item xs={12} display="flex" sx={{width: '100%',
                    position: 'fixed',
                    margin: "auto",
                    top:0,
                    right: 0}}>
                    <IconButton><KeyboardBackspaceIcon sx={{fontSize: 60, color: '#ffffff'}} onClick={()=>props.handleClose()}/></IconButton>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{width: '100%',
                    position: 'fixed',
                    margin: "auto",
                    top:65,
                    right: 0}}>
                    <Typography style={{color: '#feac0d', textAlign: 'center', fontSize: '3rem'}}>{props.initiative}</Typography>
                </Grid>
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{width: '100%',position: 'fixed',
                        margin: "auto",
                        top:200,right:0}}>
                        <Box sx={{width: '70%', height: '100%', border:'2.5px solid #feac0d',}}>
                            <CssTextField
                                sx={{  backgroundColor:'white'}}
                                value={value}
                                maxRows={15}
                                onChange={handleChange}
                                multiline
                            />
                        </Box>

                    </Grid>
                <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                    <Button style={{
                        borderRadius: 35,
                        backgroundColor: "#92d36e",
                        padding: "18px 36px",
                        fontSize: "15px",
                        position: 'fixed',
                        margin: "auto",
                        bottom:180,
                    }}
                            variant="contained">
                        CREA
                    </Button>
                </Grid>
                <Grid item display="flex" alignItems={'center'} sx={{width: '100%',
                    position: 'fixed',
                    margin: "auto",
                    bottom:0,
                    right: 0}}>
                    <CardMedia
                        component="img"
                        image={basquiaPulito}
                        sx={{
                            width: '8%',
                        }}
                        alt="Logo"
                    />
                    <Typography
                        style={{color: '#feac0d', textAlign: 'center', fontSize: '1.5rem'}}>CivicLife</Typography>

                </Grid>


            </Grid>
    );
}

export default InitiativeDetails;