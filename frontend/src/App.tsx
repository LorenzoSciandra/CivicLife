import {Routes, Route} from 'react-router-dom';
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

const App=()=>{
    return (
        <Routes>
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
            <Route path="/" element={<Login/>}/>
            <Route path='*' element={<Typography>ERROR 404</Typography>} />
        </Routes>
    )
}
export default App