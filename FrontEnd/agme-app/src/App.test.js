// react
import React from 'react';
import { Route } from 'react-router';

// components
import App from './App';
import Search from './components/search/Search';
import Dashboard from './components/Dashboard';
import CustomerProfile from './components/customerProfile/CustomerProfile';
import Register from './components/register/Register';
import Login from './components/login/Login';

// testing
import { render } from '@testing-library/react';
import { shallow } from 'enzyme';

test('renders site tagline', () => {
  const { getByText } = render(<App />);
  const linkElement = getByText(/AGME: Appointments Made Easy/i);
  expect(linkElement).toBeInTheDocument();
});

let pathMap = {};
describe('header navigation button routing', () => {
  beforeAll(() => {
    const component = shallow(<App/>);
    pathMap = component.find(Route).reduce((pathMap, route) => {
        const routeProps = route.props();
        pathMap[routeProps.path] = routeProps.component;
        return pathMap;
      }, {});
      console.log(pathMap)
  })
  it('should show Dashboard page for / route', () => {

    expect(pathMap['/']).toBe(Dashboard);
  })
  it('should show Search page for /search route', () => {
    expect(pathMap['/search']).toBe(Search);
  })
  it('should show CustomerProfile page for /customerProfile route', () => {
    expect(pathMap['/customerProfile']).toBe(CustomerProfile);
  })
  it('should show Register page for /register route', () => {
    expect(pathMap['/register']).toBe(Register);
  })
  it('should show Login page for /login route', () => {
    expect(pathMap['/login']).toBe(Login);
  })

})