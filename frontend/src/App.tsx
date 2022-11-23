import {Route, Routes} from "react-router-dom";
import MainPage from "./MainPage";
import Login from "./Login";

export default function App() {
    return (
        <>
            <Routes>
                <Route element={<Login/>}>
                    <Route path="/Main" element={<MainPage/>}/>
                </Route>
            </Routes>
        </>

    );
}
