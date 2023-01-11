import {useEffect, useState} from "react";
import {Button} from "@mui/material";
import {useNavigate} from "react-router-dom";

const SessionExpired = () => {
    const [errorReason, setErrorReason] = useState<string>('');
    const navigate= useNavigate()
    useEffect(() => {
        if (window.location.href.includes('errorReason=')) {
            const errorReason = window.location.href.split("errorReason=")[1].toString()
            setErrorReason(errorReason)
            console.log(errorReason)
        }
    })

    return (
        <div>
            <h1>Attenzione</h1>
            <h2>{errorReason}</h2>
            <Button variant="outlined" color="error" onClick={()=>{navigate('/')}}>TORNA AL LOGIN</Button>
        </div>
    );
}

export default SessionExpired;