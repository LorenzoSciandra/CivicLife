import {Route, Routes} from 'react-router-dom';
import Login from './Login/Login';
import MainPage from "./MainPage/MainPage";
import PersonalData from "./Data/PersonalData";
import UsersAdmin from "./Data/UsersAdmin";
import Initiatives from "./Initiative/Initiatives";
import InitiativeDetails from "./Initiative/initiativeDetails";
import Votations from "./Vote/Votations";
import {Typography} from "@mui/material";
import VotationDetails from "./Vote/VotationDetails";
import PartyDetails from "./Vote/PartyDetails";
import CandidateDetails from "./Vote/CandidateDetails";
import InitiativeCreateForm from "./Initiative/InitiativeCreateForm";
import LoginChoser from "./Login/LoginChoser";
import VotationsAdmin from "./Vote/VotationsAdmin";
import Error from "./Error";

type AppPropsType= {
        isMobile: boolean
}

const App = (props: AppPropsType) => {
    return (
        <Routes>
            <Route path="/login" element={<Login/>}/>
            <Route path="/home" element={<MainPage/>}/>
            <Route path="/myData" element={<PersonalData/>}/>
            <Route path="/usersAdmin" element={<UsersAdmin/>}/>
            <Route path='/initiatives' element={<Initiatives/>}/>
            <Route path='/initiativeDetails' element={<InitiativeDetails/>}/>
            <Route path='/createInitiative' element={<InitiativeCreateForm/>}/>
            <Route path='/votations' element={<Votations/>}/>
            <Route path='/votations/votationDetails' element={<VotationDetails/>}/>
            <Route path='/votations/votationDetails/partyDetails' element={<PartyDetails/>}/>
            <Route path='/votations/votationDetails/partyDetails/candidateDetails' element={<CandidateDetails/>}/>
            <Route path='/votationsAdmin' element={<VotationsAdmin/>}/>
            <Route path="/" element={<LoginChoser isMobile={props.isMobile}/>}/>
            <Route path='/error' element={<Error/>}/>
            <Route path='*' element={<Typography>ERROR 404</Typography>}/>
        </Routes>
    )
}
export default App