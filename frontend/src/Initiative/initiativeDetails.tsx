import {Button, Grid, Typography,} from "@mui/material";
import '../App.css'
import React, {useState} from "react";
import Box from '@mui/material/Box';
import {CssTextField} from "../Utils/CustomTextFields";
import {useLocation} from "react-router-dom";


const InitiativeDetails=()=>{
    const location= useLocation()
    const initiative= location.state?.initiative
    const [value, setValue]= useState(initiative)

    const handleChange=(event:React.ChangeEvent<HTMLInputElement>)=>{
        setValue(event.target.value)
    }

    return (
        <>
                {/*<Grid item xs={12} display="flex" sx={{width: '100%',*/}
                {/*    position: 'fixed',*/}
                {/*    margin: "auto",*/}
                {/*    top:0,*/}
                {/*    right: 0}}>*/}
                {/*    <IconButton><KeyboardBackspaceIcon sx={{fontSize: 60, color: '#ffffff'}} onClick={()=>props.handleClose()}/></IconButton>*/}
                {/*</Grid>*/}
    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{
        width: '100%',
        position: 'fixed',
        margin: "auto",
        top: 65,
        right: 0
    }}>
        <Typography style={{color: '#feac0d', textAlign: 'center', fontSize: '3rem'}}>{initiative}</Typography>
    </Grid><Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{
        width: '100%', position: 'fixed',
        margin: "auto",
        top: 200, right: 0
    }}>
        <Box sx={{width: '70%', height: '100%', border: '2.5px solid #feac0d',}}>
            <CssTextField
                sx={{backgroundColor: 'white'}}
                value={value}
                maxRows={15}
                onChange={handleChange}
                multiline
                InputProps={{
                    readOnly: true,
                }}/>
        </Box>

    </Grid><Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
        <Button style={{
            borderRadius: 35,
            backgroundColor: "#92d36e",
            padding: "18px 36px",
            fontSize: "15px",
            position: 'fixed',
            margin: "auto",
            bottom: 180,
        }}
                variant="contained">
            Partecipa
        </Button>
    </Grid></>

    );
}

export default InitiativeDetails;